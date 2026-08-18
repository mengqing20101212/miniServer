package ly.sceneserver.common;

/** 一次热点 Region 迁移成功后的只读结果，可用于 GM 接口、日志和自动均衡器反馈。 */
public record SceneRegionMigrationResult(
        /** 逻辑场景 ID；本服世界和跨服世界可分别统计。 */
        String sceneId,
        /** AOI、迷雾、寻路和迁移共用的一维 Region 编号。 */
        int regionIndex,
        /** 迁移开始时拥有该 Region 的 SceneShard。 */
        int sourceShardIndex,
        /** 完成切换后唯一拥有该 Region 的 SceneShard。 */
        int targetShardIndex,
        /** 本 Region 单调递增的 fencing token，用于识别迟到任务。 */
        long ownershipVersion,
        /** 本次交接的动态 SceneObject 数量，不包含共享静态地图格。 */
        int migratedObjectCount,
        /** 搬运了可见订阅或个人历史迷雾的玩家数量。 */
        int migratedPlayerStateCount,
        /** 从冻结路由到目标 FIFO 屏障完成的总耗时。 */
        long durationNanos) {
}
