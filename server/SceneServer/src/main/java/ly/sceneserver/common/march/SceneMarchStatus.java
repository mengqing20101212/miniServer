package ly.sceneserver.common.march;

/** 行军状态机；所有状态变化必须在拥有该行军的 SceneShard Tick 中执行。 */
public enum SceneMarchStatus {
    PREPARING,
    MARCHING,
    WAITING_RALLY,
    ARRIVED,
    BATTLE_PENDING,
    RETURNING,
    FINISHED,
    CANCELLED
}
