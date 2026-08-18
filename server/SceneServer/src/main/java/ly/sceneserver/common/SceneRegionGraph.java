package ly.sceneserver.common;

import java.util.BitSet;
import java.util.List;

/**
 * 静态地图对应的 Region 连通图。
 *
 * <p>相邻 Region 只有在公共边界上存在一对相邻的可行走格时才连边。连续的一段可通行
 * 边界称为一个 Portal 候选区；这里只缓存连通关系，最终使用其中哪个格子跨越边界，仍由
 * 受限的格子级 A* 根据起点、终点、地形代价和战争迷雾决定。
 */
final class SceneRegionGraph {
    // neighborMask 的四个方向位。一个 Region 最多只与上下左右四个 Region 直接相邻。
    // 这里不用 Set/Map 保存邻居，是为了让 1024 个 Region 的粗路径查询保持连续内存访问。
    private static final byte LEFT = 1;
    private static final byte RIGHT = 1 << 1;
    private static final byte UP = 1 << 2;
    private static final byte DOWN = 1 << 3;

    /** 建图时的静态地图版本；地图重新加载后旧图必须废弃。 */
    private final long mapVersion;
    /** 一个 Region 的边长，单位是地图格子，例如 32 表示 32 x 32 格。 */
    private final int regionSize;
    /** Region 列数，1000 宽且 regionSize=32 时为 ceil(1000/32)=32。 */
    private final int columns;
    /** Region 行数；最右列和最下行允许是不足 regionSize 的残块。 */
    private final int rows;
    /** 每个 Region 的四方向连通位；只有公共边界存在 Portal 才会置位。 */
    private final byte[] neighborMask;
    /** 每个 Region 内全部可行走格的平均移动代价；-1 表示整个 Region 没有可行走格。 */
    private final int[] traversalCost;
    /** 所有可通行 Region 的最小平均代价，用于 Region A* 的可采纳启发函数。 */
    private final int minimumRegionCost;
    /** 仅用于启动日志和地图质量检查的可通行 Region 数量。 */
    private final int traversableRegionCount;
    /** 所有相邻 Region 公共边界上的连续 Portal 段数量，不是边界格子的数量。 */
    private final int portalRunCount;

    private SceneRegionGraph(
            long mapVersion,
            int regionSize,
            int columns,
            int rows,
            byte[] neighborMask,
            int[] traversalCost,
            int minimumRegionCost,
            int traversableRegionCount,
            int portalRunCount) {
        this.mapVersion = mapVersion;
        this.regionSize = regionSize;
        this.columns = columns;
        this.rows = rows;
        this.neighborMask = neighborMask;
        this.traversalCost = traversalCost;
        this.minimumRegionCost = minimumRegionCost;
        this.traversableRegionCount = traversableRegionCount;
        this.portalRunCount = portalRunCount;
    }

    static SceneRegionGraph build(
            SceneStaticMap map,
            int regionSize,
            SceneTerrainCostProvider costProvider) {
        if (regionSize <= 0) {
            throw new IllegalArgumentException("regionSize must be positive");
        }
        // 第一阶段：确定粗图尺寸，并统计每个 Region 的静态地形平均代价。
        // 这里扫描的是紧凑数组，没有创建 100 万个 Tile/Node 对象。
        int columns = (map.width() + regionSize - 1) / regionSize;
        int rows = (map.height() + regionSize - 1) / regionSize;
        int regionCount = columns * rows;
        long[] costSums = new long[regionCount];
        int[] walkableCells = new int[regionCount];
        int[] traversalCost = new int[regionCount];
        byte[] neighborMask = new byte[regionCount];

        for (int y = 0; y < map.height(); y++) {
            // 同一地图行中的格子只需要计算一次 Region 行偏移，减少热循环里的除法和乘法。
            int regionRow = (y / regionSize) * columns;
            int mapRow = y * map.width();
            for (int x = 0; x < map.width(); x++) {
                int movementCost = costProvider.movementCost(
                        map.terrainAt(mapRow + x), map.flagsAt(mapRow + x));
                if (movementCost >= 0) {
                    int region = regionRow + x / regionSize;
                    costSums[region] += Math.max(1, movementCost);
                    walkableCells[region]++;
                }
            }
        }

        // 把累计值转成粗路径使用的平均代价。粗图只负责选搜索走廊，最终总成本仍由格子 A* 计算。
        int minimumRegionCost = Integer.MAX_VALUE;
        int traversableRegions = 0;
        for (int region = 0; region < regionCount; region++) {
            if (walkableCells[region] == 0) {
                traversalCost[region] = -1;
                continue;
            }
            traversalCost[region] = (int) Math.max(1L, costSums[region] / walkableCells[region]);
            minimumRegionCost = Math.min(minimumRegionCost, traversalCost[region]);
            traversableRegions++;
        }

        // 第二阶段：扫描所有左右相邻 Region 的竖直公共边界。
        // 公共边界两侧必须各有一个相邻可行走格，才能真正从左块跨入右块。
        int portalRuns = 0;
        for (int regionY = 0; regionY < rows; regionY++) {
            int minY = regionY * regionSize;
            int maxY = Math.min(map.height(), minY + regionSize);
            for (int regionX = 0; regionX + 1 < columns; regionX++) {
                int boundaryX = (regionX + 1) * regionSize;
                int runs = verticalPortalRuns(map, costProvider, boundaryX, minY, maxY);
                if (runs > 0) {
                    int leftRegion = regionY * columns + regionX;
                    int rightRegion = leftRegion + 1;
                    neighborMask[leftRegion] |= RIGHT;
                    neighborMask[rightRegion] |= LEFT;
                    portalRuns += runs;
                }
            }
        }
        // 第三阶段：以同样规则扫描所有上下相邻 Region 的水平公共边界。
        for (int regionY = 0; regionY + 1 < rows; regionY++) {
            int boundaryY = (regionY + 1) * regionSize;
            for (int regionX = 0; regionX < columns; regionX++) {
                int minX = regionX * regionSize;
                int maxX = Math.min(map.width(), minX + regionSize);
                int runs = horizontalPortalRuns(map, costProvider, boundaryY, minX, maxX);
                if (runs > 0) {
                    int upperRegion = regionY * columns + regionX;
                    int lowerRegion = upperRegion + columns;
                    neighborMask[upperRegion] |= DOWN;
                    neighborMask[lowerRegion] |= UP;
                    portalRuns += runs;
                }
            }
        }

        return new SceneRegionGraph(
                map.modificationVersion(),
                regionSize,
                columns,
                rows,
                neighborMask,
                traversalCost,
                minimumRegionCost == Integer.MAX_VALUE ? 1 : minimumRegionCost,
                traversableRegions,
                portalRuns);
    }

    boolean matches(SceneStaticMap map, int expectedRegionSize) {
        // regionSize 改变时 Region 编号、边界位置都会改变，也必须重新建图。
        return mapVersion == map.modificationVersion() && regionSize == expectedRegionSize;
    }

    int regionSize() {
        return regionSize;
    }

    int columns() {
        return columns;
    }

    int rows() {
        return rows;
    }

    int regionCount() {
        return neighborMask.length;
    }

    int traversableRegionCount() {
        return traversableRegionCount;
    }

    int portalRunCount() {
        return portalRunCount;
    }

    int regionIndex(int x, int y) {
        // Region 采用行优先的一维编号，与 AOI 和个人迷雾 BitSet 使用相同索引规则。
        return (y / regionSize) * columns + x / regionSize;
    }

    int traversalCost(int regionIndex) {
        return traversalCost[regionIndex];
    }

    int minimumRegionCost() {
        return minimumRegionCost;
    }

    /** 0=左、1=右、2=上、3=下；不存在对应 Portal 时返回 -1。 */
    int neighbor(int regionIndex, int direction) {
        byte requiredMask = switch (direction) {
            case 0 -> LEFT;
            case 1 -> RIGHT;
            case 2 -> UP;
            case 3 -> DOWN;
            default -> throw new IllegalArgumentException("invalid region direction: " + direction);
        };
        if ((neighborMask[regionIndex] & requiredMask) == 0) {
            return -1;
        }
        return switch (direction) {
            case 0 -> regionIndex - 1;
            case 1 -> regionIndex + 1;
            case 2 -> regionIndex - columns;
            case 3 -> regionIndex + columns;
            default -> -1;
        };
    }

    int heuristic(int fromRegion, int targetRegion) {
        int fromX = fromRegion % columns;
        int fromY = fromRegion / columns;
        int targetX = targetRegion % columns;
        int targetY = targetRegion / columns;
        // 只允许四方向移动，因此使用曼哈顿距离；乘最小 Region 代价保证不会高估。
        return (Math.abs(fromX - targetX) + Math.abs(fromY - targetY)) * minimumRegionCost;
    }

    /** 将粗路径扩成格子级 A* 可以使用的 Region 走廊。 */
    BitSet corridor(List<Integer> regionRoute, int padding) {
        // padding=0 只允许粗路径上的块；padding=1 会把上下左右及斜角相邻块也放进细路径走廊。
        // 一圈缓冲可以让格子 A* 绕过块内局部障碍，同时仍远小于搜索整张 100 万格地图。
        BitSet result = new BitSet(regionCount());
        int safePadding = Math.max(0, padding);
        for (int region : regionRoute) {
            int centerX = region % columns;
            int centerY = region / columns;
            int minY = Math.max(0, centerY - safePadding);
            int maxY = Math.min(rows - 1, centerY + safePadding);
            int minX = Math.max(0, centerX - safePadding);
            int maxX = Math.min(columns - 1, centerX + safePadding);
            for (int y = minY; y <= maxY; y++) {
                result.set(y * columns + minX, y * columns + maxX + 1);
            }
        }
        return result;
    }

    private static int verticalPortalRuns(
            SceneStaticMap map,
            SceneTerrainCostProvider provider,
            int rightX,
            int minY,
            int maxY) {
        if (rightX <= 0 || rightX >= map.width()) {
            return 0;
        }
        // 统计“连续通道段”而不是逐格计数。例如连续 8 对可跨越格只算一个 Portal 候选区。
        int runs = 0;
        boolean insideRun = false;
        for (int y = minY; y < maxY; y++) {
            boolean passable = isWalkable(map, provider, rightX - 1, y)
                    && isWalkable(map, provider, rightX, y);
            if (passable && !insideRun) {
                runs++;
            }
            insideRun = passable;
        }
        return runs;
    }

    private static int horizontalPortalRuns(
            SceneStaticMap map,
            SceneTerrainCostProvider provider,
            int lowerY,
            int minX,
            int maxX) {
        if (lowerY <= 0 || lowerY >= map.height()) {
            return 0;
        }
        // 与竖直边界相同：上方格和下方格必须同时可行走，才允许跨 Region。
        int runs = 0;
        boolean insideRun = false;
        for (int x = minX; x < maxX; x++) {
            boolean passable = isWalkable(map, provider, x, lowerY - 1)
                    && isWalkable(map, provider, x, lowerY);
            if (passable && !insideRun) {
                runs++;
            }
            insideRun = passable;
        }
        return runs;
    }

    private static boolean isWalkable(
            SceneStaticMap map,
            SceneTerrainCostProvider provider,
            int x,
            int y) {
        // 统一经过 TerrainCostProvider 判断，避免粗图与格子 A* 对某种地形的可达性理解不一致。
        int index = y * map.width() + x;
        return provider.movementCost(map.terrainAt(index), map.flagsAt(index)) >= 0;
    }
}
