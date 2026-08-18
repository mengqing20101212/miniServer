package ly.sceneserver.common.march;

/** 集结成员状态；未在发车时间前到达的成员会被明确标记为 EXCLUDED。 */
public enum SceneRallyMemberStatus {
    JOINING,
    READY,
    MARCHING,
    BATTLE_PENDING,
    RETURNING,
    FINISHED,
    LEFT,
    EXCLUDED
}
