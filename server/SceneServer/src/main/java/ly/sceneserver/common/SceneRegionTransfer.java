package ly.sceneserver.common;

import java.util.List;

/**
 * 从源 SceneShard 导出、由迁移线程校验、最终安装到目标 SceneShard 的 Region 数据包。
 *
 * <p>当前迁移只发生在同一个 SceneServer JVM 内，因此动态对象采用“独占引用交接”：源 Tick
 * 从所有索引移除对象后不再访问它，目标 Tick 安装前迁移线程也只校验元数据、不修改对象。
 * 这样不需要把复杂玩法状态序列化成 JSON。未来扩展为跨进程迁移时，应为该数据包增加明确的
 * Protobuf/二进制 codec，而不是直接发送 Java 对象引用。
 */
record SceneRegionTransfer(
        /** Region、源/目标 Shard 和所有权版本组成的迁移凭证。 */
        SceneRegionMigrationTicket ticket,
        /** 已从源端全部索引摘除、正处于独占交接状态的动态对象。 */
        List<SceneObject> objects,
        /** 与该 Region 有关的在线 AOI 订阅和离线个人迷雾状态。 */
        List<SceneRegionPlayerTransfer> players) {

    SceneRegionTransfer {
        objects = List.copyOf(objects);
        players = List.copyOf(players);
    }
}

/**
 * 一个玩家与被迁移 Region 相关的 AOI 和个人战争迷雾状态。
 *
 * <p>visible 为 true 时同时携带视野层级和标签过滤参数，以便目标 Shard 重建订阅；discovered
 * 独立于在线状态，即使玩家已经离线也必须迁移，避免重启前异步落库得到不完整迷雾。
 */
record SceneRegionPlayerTransfer(
        /** 玩家 ID；不同玩家的战争迷雾绝不能合并。 */
        long playerId,
        /** 玩家当前相机是否覆盖这个 Region。 */
        boolean visible,
        /** 玩家历史上是否已经解锁这个 Region。 */
        boolean discovered,
        /** visible=true 时的全局相机中心块，用于目标端恢复 ViewerState。 */
        ScenePoint centerBlock,
        /** 客户端当前缩放层；决定目标端 AOI 对象过滤级别。 */
        SceneViewLevel viewLevel,
        /** 客户端额外请求的数据标签掩码。 */
        long requestedTagMask) {

    SceneRegionPlayerTransfer {
        if (playerId <= 0L) {
            throw new IllegalArgumentException("playerId must be positive");
        }
        if (visible && (centerBlock == null || viewLevel == null)) {
            throw new IllegalArgumentException("visible player must carry viewer metadata");
        }
    }
}
