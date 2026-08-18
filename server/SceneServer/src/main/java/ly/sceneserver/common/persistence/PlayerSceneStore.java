package ly.sceneserver.common.persistence;

import java.util.List;

/** 玩家场景投影存储；实现必须按 revision 幂等，旧版本不能覆盖新版本。 */
public interface PlayerSceneStore {
    /** 按 playerId 升序分页加载有效投影，数据库异常必须抛出，不能降级为空地图。 */
    List<PlayerSceneProjection> loadActivePage(String sceneId, long afterPlayerId, int limit);

    /** 插入或更新投影；相同 revision 重放成功，较小 revision 被安全忽略。 */
    void upsert(PlayerSceneProjection projection);
}
