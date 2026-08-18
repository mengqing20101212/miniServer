package ly.sceneserver.common;

import java.util.ArrayList;
import java.util.List;

/** A* 结果；completedTick 证明结果已经回投 SceneShard Tick 后再交给业务。 */
public record ScenePathResult(
        /** 寻路状态；只有 OK 时 points 和 totalCost 才表示有效路径。 */
        ScenePathStatus status,
        /** 起点到终点的完整连续格子序列，包含起点和终点。 */
        List<ScenePoint> points,
        /** 按地形移动代价累计的总成本，不等同于路径格子数量。 */
        int totalCost,
        /** 格子级 A* 实际关闭的节点数，走廊和回退两次搜索会累计。 */
        int visitedNodes,
        /** 异步结果回投 SceneShard 后所在的 Tick；寻路线程内部生成时为 0。 */
        long completedTick) {

    public ScenePathResult {
        // 防止调用方持有的可变 List 在结果返回后继续修改权威路径。
        points = List.copyOf(points);
    }

    public static ScenePathResult failure(ScenePathStatus status, int visitedNodes) {
        return new ScenePathResult(status, List.of(), 0, visitedNodes, 0L);
    }

    public ScenePathResult completedOn(long tickNumber) {
        return new ScenePathResult(status, points, totalCost, visitedNodes, tickNumber);
    }

    /**
     * 按 Region 拆分最终格子路径，直接给出每个块实际采用的入口、出口和块内路径。
     */
    public List<SceneRegionPathSegment> regionSegments(int mapWidth, int regionSize) {
        if (mapWidth <= 0 || regionSize <= 0) {
            throw new IllegalArgumentException("mapWidth and regionSize must be positive");
        }
        if (points.isEmpty()) {
            return List.of();
        }
        // Region 编号与 AOI/迷雾一致：regionY * blockColumns + regionX。
        int blockColumns = (mapWidth + regionSize - 1) / regionSize;
        ArrayList<SceneRegionPathSegment> segments = new ArrayList<>();
        int startOffset = 0;
        int currentRegion = regionIndex(points.getFirst(), regionSize, blockColumns);
        for (int i = 1; i <= points.size(); i++) {
            // i==points.size() 使用 -1 哨兵，确保最后一个 Region 片段也会被提交。
            int nextRegion = i == points.size()
                    ? -1
                    : regionIndex(points.get(i), regionSize, blockColumns);
            if (nextRegion == currentRegion) {
                continue;
            }
            // [startOffset, i) 是最终路径在 currentRegion 中连续经过的全部格子。
            // 第一格是入口，最后一格是出口；首尾 Region 分别用全局起点/终点充当入口/出口。
            List<ScenePoint> regionPoints = points.subList(startOffset, i);
            segments.add(new SceneRegionPathSegment(
                    currentRegion,
                    currentRegion % blockColumns,
                    currentRegion / blockColumns,
                    regionPoints.getFirst(),
                    regionPoints.getLast(),
                    regionPoints));
            startOffset = i;
            currentRegion = nextRegion;
        }
        return List.copyOf(segments);
    }

    private static int regionIndex(ScenePoint point, int regionSize, int blockColumns) {
        return (point.y() / regionSize) * blockColumns + point.x() / regionSize;
    }
}
