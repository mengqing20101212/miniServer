package ly.logic.asset;

/** 资产变更的业务来源，用于审计日志。 */
public enum ChangeReason {
    HERO_LEVEL_UP,
    HERO_STAR_UP,
    HERO_AWAKEN,
    QUEST_REWARD,
    GM,
    OTHER
}
