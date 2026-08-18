package ly.sceneserver.common;

import java.util.List;

/**
 * 最终格子路径在一个 Region 内的连续片段。
 *
 * <p>entry 是进入本块后的第一个格子，exit 是离开本块前的最后一个格子；相邻片段的
 * exit/entry 必须是公共边界两侧相邻的可行走格，它们共同构成这次行军实际使用的 Portal。
 */
public record SceneRegionPathSegment(
        /** AOI/迷雾统一使用的一维 Region 编号。 */
        int regionIndex,
        /** Region 的横向块坐标，不是地图格子的 X。 */
        int regionX,
        /** Region 的纵向块坐标，不是地图格子的 Y。 */
        int regionY,
        /** 最终路径进入本 Region 后的第一个地图格；首块为全局起点。 */
        ScenePoint entry,
        /** 最终路径离开本 Region 前的最后一个地图格；末块为全局终点。 */
        ScenePoint exit,
        /** 从 entry 到 exit 的连续格子路径，包含 entry 和 exit。 */
        List<ScenePoint> points) {

    public SceneRegionPathSegment {
        if (regionIndex < 0 || regionX < 0 || regionY < 0
                || entry == null || exit == null || points == null || points.isEmpty()) {
            throw new IllegalArgumentException("invalid region path segment");
        }
        // 片段会挂到行军路径或用于诊断，必须保持不可变，不能被业务线程原地修改。
        points = List.copyOf(points);
    }
}
