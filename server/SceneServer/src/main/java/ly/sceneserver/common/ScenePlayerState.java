package ly.sceneserver.common;

/** 假数据中的玩家地图状态；真实养成数据仍由 GameServer 管理。 */
public record ScenePlayerState(long playerId, int level, int power) {
}
