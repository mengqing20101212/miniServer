package ly.sceneserver.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 面向 100 万格地图的 Region + 格子两级四方向 A*。
 *
 * <p>先利用静态地图公共边界上的 Portal 候选区寻找 Region 粗路径，再把粗路径扩成有限
 * Region 走廊，由格子级 A* 决定每块的真实入口、出口和块内路径。格子级搜索仍然检查
 * 地形代价、不可行走标记和玩家个人战争迷雾，因此粗路径不能绕过地图规则。
 *
 * <p>每个寻路线程复用 int[] 工作区和原生整数最小堆，避免每次请求创建大量 Node 对象或
 * 清空 100 万长度数组。搜索受 maxVisitedNodes 硬限制，热点时不会无限吞噬 CPU。
 */
public final class ScenePathfinder {
    /** 粗路径默认向外扩一圈 Region，兼顾搜索范围和块内障碍绕行能力。 */
    private static final int DEFAULT_REGION_PADDING = 1;

    /** 地形编号和 flags 到移动代价的唯一解释器，粗图与细图必须共用。 */
    private final SceneTerrainCostProvider terrainCostProvider;
    /** 格子 A* 曼哈顿启发使用的最小单步代价，必须保证不会高估。 */
    private final int minimumStepCost;
    /** Region 粗路径周围允许格子 A* 搜索的额外块圈数。 */
    private final int regionPadding;
    /** 每个寻路线程独享并复用数组工作区，线程之间不共享可变搜索状态。 */
    private final ThreadLocal<Workspace> workspaces = ThreadLocal.withInitial(Workspace::new);
    /**
     * 静态地图到 Region 图的弱引用缓存。
     *
     * <p>同一个 ScenePathfinder 服务多个逻辑场景；WeakHashMap 防止已经销毁的地图仅因寻路缓存
     * 无法回收。内部按 regionSize 再分组，便于测试或未来同图使用不同粗粒度。
     */
    private final Map<SceneStaticMap, Map<Integer, SceneRegionGraph>> regionGraphs = new WeakHashMap<>();

    public ScenePathfinder(SceneTerrainCostProvider terrainCostProvider) {
        this(
                terrainCostProvider,
                Math.max(0, Integer.getInteger(
                        "slg.scene.path.region-padding",
                        DEFAULT_REGION_PADDING)));
    }

    ScenePathfinder(SceneTerrainCostProvider terrainCostProvider, int regionPadding) {
        if (terrainCostProvider == null || regionPadding < 0) {
            throw new IllegalArgumentException("invalid pathfinder parameters");
        }
        this.terrainCostProvider = terrainCostProvider;
        this.minimumStepCost = Math.max(0, terrainCostProvider.minimumMovementCost());
        this.regionPadding = regionPadding;
    }

    /** 在 SceneRuntime 启动阶段预建 Region 连通图，避免第一个玩家请求承担扫描成本。 */
    SceneRegionGraph prepare(SceneStaticMap map, int regionSize) {
        return regionGraph(map, regionSize);
    }

    public ScenePathResult find(
            SceneStaticMap map,
            ScenePathRequest request,
            SceneVisibilitySnapshot visibility) {
        // 1. 先做常量时间校验。越界、起终点受迷雾限制或落在不可行走格时，不进入任何 A*。
        if (map == null || request == null || visibility == null) {
            return ScenePathResult.failure(ScenePathStatus.INVALID_ARGUMENT, 0);
        }
        if (!inBounds(map, request.start()) || !inBounds(map, request.target())) {
            return ScenePathResult.failure(ScenePathStatus.OUT_OF_BOUNDS, 0);
        }
        int regionSize = visibility.regionSize();
        if (regionSize <= 0) {
            return ScenePathResult.failure(ScenePathStatus.INVALID_ARGUMENT, 0);
        }
        int expectedBlockColumns = (map.width() + regionSize - 1) / regionSize;
        if (visibility.blockColumns() != expectedBlockColumns) {
            return ScenePathResult.failure(ScenePathStatus.INVALID_ARGUMENT, 0);
        }
        if (!visibility.allows(request.start().x(), request.start().y(), request.fogPolicy())
                || !visibility.allows(request.target().x(), request.target().y(), request.fogPolicy())) {
            return ScenePathResult.failure(ScenePathStatus.FOG_BLOCKED, 0);
        }

        int width = map.width();
        int startIndex = request.start().y() * width + request.start().x();
        int targetIndex = request.target().y() * width + request.target().x();
        if (movementCost(map, startIndex) < 0 || movementCost(map, targetIndex) < 0) {
            return ScenePathResult.failure(ScenePathStatus.PATH_NOT_FOUND, 0);
        }
        if (startIndex == targetIndex) {
            // 起点即终点时仍返回一个点，visitedNodes=1 表示检查了该格。
            return new ScenePathResult(
                    ScenePathStatus.OK, List.of(request.start()), 0, 1, 0L);
        }

        int maxVisitedNodes = request.effectiveMaxVisitedNodes();
        if (regionSize <= 1) {
            // regionSize=1 常用于单元测试或极小地图，此时粗图和格子图完全等价，直接走细路径。
            return findCells(map, request, visibility, null, maxVisitedNodes, 0);
        }

        // 2. 在约 1024 个 Region 上寻找粗路径。粗图只选择候选走廊，不直接作为最终行军路径。
        SceneRegionGraph graph = regionGraph(map, regionSize);
        int startRegion = graph.regionIndex(request.start().x(), request.start().y());
        int targetRegion = graph.regionIndex(request.target().x(), request.target().y());
        if (startRegion == targetRegion) {
            // 同块请求也先限制在当前块及 padding 范围，必要时仍会回退全图，避免局部请求扫描全图。
            return findWithCorridorFallback(
                    map,
                    request,
                    visibility,
                    graph,
                    List.of(startRegion),
                    maxVisitedNodes);
        }

        List<Integer> regionRoute = findRegionRoute(
                graph, startRegion, targetRegion, request.fogPolicy(), visibility);
        if (regionRoute.isEmpty()) {
            // Region 边由真实 Portal 生成并同时经过本玩家迷雾过滤；当前地图约束和迷雾策略下无粗路径。
            return ScenePathResult.failure(ScenePathStatus.PATH_NOT_FOUND, 0);
        }

        return findWithCorridorFallback(
                map, request, visibility, graph, regionRoute, maxVisitedNodes);
    }

    private ScenePathResult findWithCorridorFallback(
            SceneStaticMap map,
            ScenePathRequest request,
            SceneVisibilitySnapshot visibility,
            SceneRegionGraph graph,
            List<Integer> regionRoute,
            int maxVisitedNodes) {
        // 3. 将 Region 序列展开为 BitSet 走廊。格子 A* 用一次位测试即可判断候选格是否在走廊内。
        BitSet corridorRegions = graph.corridor(regionRoute, regionPadding);
        RegionCorridor corridor = new RegionCorridor(
                graph.regionSize(), graph.columns(), corridorRegions);
        ScenePathResult corridorResult = findCells(
                map, request, visibility, corridor, maxVisitedNodes, 0);
        if (corridorResult.status() != ScenePathStatus.PATH_NOT_FOUND) {
            // OK 直接返回；LIMIT_EXCEEDED 也必须直接返回，禁止突破调用方给出的 CPU 预算。
            return corridorResult;
        }

        // Region 只缓存“边界可跨越”，块内部可能存在互不连通的障碍区。走廊失败时使用剩余
        // 预算回退到全图格子 A*，避免把粗图的近似性错误解释为真正无路。
        // 4. 走廊穷尽但未找到路径时，可能是某个 Region 内部存在两个互不连通的区域。
        // 回退搜索只能使用第一次搜索后剩余的节点预算，两次尝试总和不能超过 maxVisitedNodes。
        int remainingBudget = maxVisitedNodes - corridorResult.visitedNodes();
        if (remainingBudget <= 0) {
            return ScenePathResult.failure(
                    ScenePathStatus.LIMIT_EXCEEDED,
                    corridorResult.visitedNodes());
        }
        return findCells(
                map,
                request,
                visibility,
                null,
                remainingBudget,
                corridorResult.visitedNodes());
    }

    private ScenePathResult findCells(
            SceneStaticMap map,
            ScenePathRequest request,
            SceneVisibilitySnapshot visibility,
            RegionCorridor corridor,
            int maxVisitedNodes,
            int visitedOffset) {
        // 细路径搜索的输出才是权威行军路径。它逐格检查地形、flags、个人迷雾和可选 Region 走廊。
        int width = map.width();
        int startIndex = request.start().y() * width + request.start().x();
        int targetIndex = request.target().y() * width + request.target().x();
        // beginCells 通过 epoch 复用旧数组，不需要每次 Arrays.fill 一百万个位置。
        Workspace workspace = workspaces.get();
        workspace.beginCells(map.cellCount(), maxVisitedNodes);
        int epoch = workspace.cellEpoch;
        workspace.seenEpoch[startIndex] = epoch;
        workspace.gCost[startIndex] = 0;
        workspace.parent[startIndex] = -1;
        workspace.cellHeap.push(startIndex, 0, heuristic(width, startIndex, targetIndex));

        int visited = 0;
        while (!workspace.cellHeap.isEmpty()) {
            int current = workspace.cellHeap.pop();
            int currentG = workspace.cellHeap.poppedG;
            if (workspace.closedEpoch[current] == epoch
                    || workspace.seenEpoch[current] != epoch
                    || workspace.gCost[current] != currentG) {
                continue;
            }
            // 同一个格可能以不同 gCost 多次入堆；这里只关闭当前仍是最优记录的候选项。
            workspace.closedEpoch[current] = epoch;
            visited++;
            if (current == targetIndex) {
                return buildResult(
                        map, workspace, startIndex, targetIndex, visitedOffset + visited);
            }
            if (visited >= maxVisitedNodes) {
                // 先检查 current 是否目标，再检查上限，因此“第 N 个节点就是终点”仍可成功。
                return ScenePathResult.failure(
                        ScenePathStatus.LIMIT_EXCEEDED,
                        visitedOffset + visited);
            }

            int x = current % width;
            int y = current / width;
            if (!visitNeighbor(
                            map, request, visibility, corridor, workspace,
                            current, currentG, x - 1, y, targetIndex)
                    || !visitNeighbor(
                            map, request, visibility, corridor, workspace,
                            current, currentG, x + 1, y, targetIndex)
                    || !visitNeighbor(
                            map, request, visibility, corridor, workspace,
                            current, currentG, x, y - 1, targetIndex)
                    || !visitNeighbor(
                            map, request, visibility, corridor, workspace,
                            current, currentG, x, y + 1, targetIndex)) {
                return ScenePathResult.failure(
                        ScenePathStatus.LIMIT_EXCEEDED,
                        visitedOffset + visited);
            }
        }
        return ScenePathResult.failure(
                ScenePathStatus.PATH_NOT_FOUND,
                visitedOffset + visited);
    }

    private boolean visitNeighbor(
            SceneStaticMap map,
            ScenePathRequest request,
            SceneVisibilitySnapshot visibility,
            RegionCorridor corridor,
            Workspace workspace,
            int current,
            int currentG,
            int x,
            int y,
            int targetIndex) {
        if (x < 0 || x >= map.width() || y < 0 || y >= map.height()) {
            return true;
        }
        // 粗路径限制只负责缩小范围；没有走廊或回退搜索时 corridor 为 null。
        if (corridor != null && !corridor.allows(x, y)) {
            return true;
        }
        // 玩家迷雾是每次请求的只读快照，同一地图上不同玩家可能得到不同的可达结果。
        if (!visibility.allows(x, y, request.fogPolicy())) {
            return true;
        }
        int next = y * map.width() + x;
        if (workspace.closedEpoch[next] == workspace.cellEpoch) {
            return true;
        }
        // 负代价表示水域、山墙、关闭城门等不可进入格；正代价同时决定最短时间路径。
        int stepCost = movementCost(map, next);
        if (stepCost < 0) {
            return true;
        }
        // gCost 是起点到 next 的真实累计地形代价，不能用格子数量代替。
        int nextG = currentG + stepCost;
        if (workspace.seenEpoch[next] == workspace.cellEpoch && nextG >= workspace.gCost[next]) {
            return true;
        }
        workspace.seenEpoch[next] = workspace.cellEpoch;
        workspace.gCost[next] = nextG;
        workspace.parent[next] = current;
        return workspace.cellHeap.push(
                next,
                nextG,
                nextG + heuristic(map.width(), next, targetIndex));
    }

    private List<Integer> findRegionRoute(
            SceneRegionGraph graph,
            int startRegion,
            int targetRegion,
            SceneFogPolicy fogPolicy,
            SceneVisibilitySnapshot visibility) {
        // Region A* 使用独立的小工作区。1000 x 1000 / 32 的标准地图仅有 1024 个节点。
        Workspace workspace = workspaces.get();
        workspace.beginRegions(graph.regionCount());
        int epoch = workspace.regionEpoch;
        workspace.regionSeenEpoch[startRegion] = epoch;
        workspace.regionGCost[startRegion] = 0;
        workspace.regionParent[startRegion] = -1;
        workspace.regionHeap.push(
                startRegion, 0, graph.heuristic(startRegion, targetRegion));

        while (!workspace.regionHeap.isEmpty()) {
            int current = workspace.regionHeap.pop();
            int currentG = workspace.regionHeap.poppedG;
            if (workspace.regionClosedEpoch[current] == epoch
                    || workspace.regionSeenEpoch[current] != epoch
                    || workspace.regionGCost[current] != currentG) {
                continue;
            }
            workspace.regionClosedEpoch[current] = epoch;
            if (current == targetRegion) {
                return buildRegionRoute(workspace, startRegion, targetRegion);
            }
            for (int direction = 0; direction < 4; direction++) {
                // graph.neighbor 只有在公共边界存在 Portal 候选区时才返回相邻 Region。
                int next = graph.neighbor(current, direction);
                if (next < 0
                        || workspace.regionClosedEpoch[next] == epoch
                        || graph.traversalCost(next) < 0
                        // 粗路径同样受玩家个人迷雾约束，不能先穿过未知块再让细路径修正。
                        || !visibility.allowsRegion(next, fogPolicy)) {
                    continue;
                }
                int nextG = currentG + graph.traversalCost(next);
                if (workspace.regionSeenEpoch[next] == epoch
                        && nextG >= workspace.regionGCost[next]) {
                    continue;
                }
                workspace.regionSeenEpoch[next] = epoch;
                workspace.regionGCost[next] = nextG;
                workspace.regionParent[next] = current;
                if (!workspace.regionHeap.push(
                        next,
                        nextG,
                        nextG + graph.heuristic(next, targetRegion))) {
                    return List.of();
                }
            }
        }
        return List.of();
    }

    private static List<Integer> buildRegionRoute(
            Workspace workspace,
            int startRegion,
            int targetRegion) {
        // parent 从终点指向起点，先反向收集，再翻转为行军方向。
        ArrayList<Integer> reversed = new ArrayList<>();
        for (int current = targetRegion; current >= 0; current = workspace.regionParent[current]) {
            reversed.add(current);
            if (current == startRegion) {
                break;
            }
        }
        Collections.reverse(reversed);
        return List.copyOf(reversed);
    }

    private ScenePathResult buildResult(
            SceneStaticMap map,
            Workspace workspace,
            int startIndex,
            int targetIndex,
            int visited) {
        // 最终路径保留每一个格子。跨 Region 的相邻两点随后可以拆成出口/入口 Portal。
        ArrayList<ScenePoint> reversed = new ArrayList<>();
        for (int current = targetIndex; current >= 0; current = workspace.parent[current]) {
            reversed.add(new ScenePoint(current % map.width(), current / map.width()));
            if (current == startIndex) {
                break;
            }
        }
        Collections.reverse(reversed);
        return new ScenePathResult(
                ScenePathStatus.OK,
                reversed,
                workspace.gCost[targetIndex],
                visited,
                0L);
    }

    private SceneRegionGraph regionGraph(SceneStaticMap map, int regionSize) {
        // WeakHashMap 不是并发容器；建图只发生在启动或静态地图版本变化后，因此用简单同步即可。
        synchronized (regionGraphs) {
            Map<Integer, SceneRegionGraph> byRegionSize =
                    regionGraphs.computeIfAbsent(map, ignored -> new HashMap<>());
            SceneRegionGraph graph = byRegionSize.get(regionSize);
            if (graph == null || !graph.matches(map, regionSize)) {
                // 正常线上地图在 start() 后冻结，不会走到重建；版本判断主要保护测试和重新加载流程。
                graph = SceneRegionGraph.build(map, regionSize, terrainCostProvider);
                byRegionSize.put(regionSize, graph);
            }
            return graph;
        }
    }

    private int movementCost(SceneStaticMap map, int index) {
        return terrainCostProvider.movementCost(map.terrainAt(index), map.flagsAt(index));
    }

    private int heuristic(int width, int fromIndex, int targetIndex) {
        int fromX = fromIndex % width;
        int fromY = fromIndex / width;
        int targetX = targetIndex % width;
        int targetY = targetIndex / width;
        return (Math.abs(fromX - targetX) + Math.abs(fromY - targetY)) * minimumStepCost;
    }

    private boolean inBounds(SceneStaticMap map, ScenePoint point) {
        return point.x() >= 0
                && point.x() < map.width()
                && point.y() >= 0
                && point.y() < map.height();
    }

    private record RegionCorridor(int regionSize, int blockColumns, BitSet allowedRegions) {
        private boolean allows(int x, int y) {
            // 与 AOI、战争迷雾使用完全一致的行优先 Region 编号。
            int region = (y / regionSize) * blockColumns + x / regionSize;
            return allowedRegions.get(region);
        }
    }

    private static final class Workspace {
        // 格子级数组按地图 cellIndex 访问。epoch 数组用于区分本次搜索和历史搜索。
        private int[] seenEpoch = new int[0];
        private int[] closedEpoch = new int[0];
        private int[] gCost = new int[0];
        private int[] parent = new int[0];
        private int cellEpoch;
        private final IntMinHeap cellHeap = new IntMinHeap();

        // Region 级数组规模通常只有约 1024，与一百万格工作区分开，避免互相清理或覆盖。
        private int[] regionSeenEpoch = new int[0];
        private int[] regionClosedEpoch = new int[0];
        private int[] regionGCost = new int[0];
        private int[] regionParent = new int[0];
        private int regionEpoch;
        private final IntMinHeap regionHeap = new IntMinHeap();

        private void beginCells(int cellCount, int maxVisitedNodes) {
            if (seenEpoch.length < cellCount) {
                seenEpoch = new int[cellCount];
                closedEpoch = new int[cellCount];
                gCost = new int[cellCount];
                parent = new int[cellCount];
                cellEpoch = 0;
            }
            cellEpoch++;
            if (cellEpoch == Integer.MAX_VALUE) {
                // epoch 极少回绕；回绕时才真正清空标记数组，避免旧标记与新搜索冲突。
                Arrays.fill(seenEpoch, 0);
                Arrays.fill(closedEpoch, 0);
                cellEpoch = 1;
            }
            // 每个已访问格最多允许四个候选项，超过后按搜索超限快速失败。
            cellHeap.reset(Math.max(64, Math.min(Integer.MAX_VALUE / 4, maxVisitedNodes * 4)));
        }

        private void beginRegions(int regionCount) {
            if (regionSeenEpoch.length < regionCount) {
                regionSeenEpoch = new int[regionCount];
                regionClosedEpoch = new int[regionCount];
                regionGCost = new int[regionCount];
                regionParent = new int[regionCount];
                regionEpoch = 0;
            }
            regionEpoch++;
            if (regionEpoch == Integer.MAX_VALUE) {
                // Region 标记采用与格子工作区相同的 epoch 复用策略。
                Arrays.fill(regionSeenEpoch, 0);
                Arrays.fill(regionClosedEpoch, 0);
                regionEpoch = 1;
            }
            regionHeap.reset(Math.max(16, Math.min(Integer.MAX_VALUE / 4, regionCount * 4)));
        }
    }

    /** 不创建 Node 对象的最小堆；允许旧候选重复入堆，出堆时通过 gCost 丢弃陈旧项。 */
    private static final class IntMinHeap {
        // nodes/gScores/fScores 使用并行原生数组，避免 PriorityQueue<Node> 的对象分配和 GC 压力。
        private int[] nodes = new int[1_024];
        private int[] gScores = new int[1_024];
        private int[] fScores = new int[1_024];
        private int size;
        private int maxSize;
        private int poppedG;

        private void reset(int maxSize) {
            size = 0;
            this.maxSize = maxSize;
        }

        private boolean isEmpty() {
            return size == 0;
        }

        private boolean push(int node, int gScore, int fScore) {
            if (size >= maxSize) {
                // 堆容量与节点预算绑定；返回 false 让上层中止本次搜索，而不是继续扩容失控。
                return false;
            }
            ensureCapacity(size + 1);
            int index = size++;
            while (index > 0) {
                int parentIndex = (index - 1) >>> 1;
                if (compare(fScores[parentIndex], nodes[parentIndex], fScore, node) <= 0) {
                    break;
                }
                nodes[index] = nodes[parentIndex];
                gScores[index] = gScores[parentIndex];
                fScores[index] = fScores[parentIndex];
                index = parentIndex;
            }
            nodes[index] = node;
            gScores[index] = gScore;
            fScores[index] = fScore;
            return true;
        }

        private int pop() {
            // 删除堆顶后把最后一个节点下沉，保持 fCost 优先、nodeIndex 次优先的稳定顺序。
            int result = nodes[0];
            poppedG = gScores[0];
            int lastIndex = --size;
            if (lastIndex == 0) {
                return result;
            }
            int lastNode = nodes[lastIndex];
            int lastG = gScores[lastIndex];
            int lastF = fScores[lastIndex];
            int index = 0;
            while (true) {
                int left = index * 2 + 1;
                if (left >= size) {
                    break;
                }
                int right = left + 1;
                int child = right < size
                                && compare(fScores[right], nodes[right], fScores[left], nodes[left]) < 0
                        ? right
                        : left;
                if (compare(lastF, lastNode, fScores[child], nodes[child]) <= 0) {
                    break;
                }
                nodes[index] = nodes[child];
                gScores[index] = gScores[child];
                fScores[index] = fScores[child];
                index = child;
            }
            nodes[index] = lastNode;
            gScores[index] = lastG;
            fScores[index] = lastF;
            return result;
        }

        private int compare(int leftF, int leftNode, int rightF, int rightNode) {
            // nodeIndex 作为相同 fCost 时的确定性 tie-break，便于测试和线上复现同一条路径。
            int byCost = Integer.compare(leftF, rightF);
            return byCost != 0 ? byCost : Integer.compare(leftNode, rightNode);
        }

        private void ensureCapacity(int required) {
            if (required <= nodes.length) {
                return;
            }
            int newLength = Math.min(maxSize, Math.max(required, nodes.length << 1));
            nodes = Arrays.copyOf(nodes, newLength);
            gScores = Arrays.copyOf(gScores, newLength);
            fScores = Arrays.copyOf(fScores, newLength);
        }
    }
}
