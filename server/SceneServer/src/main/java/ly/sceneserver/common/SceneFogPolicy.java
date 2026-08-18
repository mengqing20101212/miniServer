package ly.sceneserver.common;

/** 寻路时如何处理战争迷雾。 */
public enum SceneFogPolicy {
    /** 服务器内部移动或系统寻路，不检查玩家视野。 */
    IGNORE,
    /** 只能经过玩家历史探索过的 AOI 块。 */
    DISCOVERED_ONLY,
    /** 只能经过玩家当前正在观察的 AOI 块。 */
    VISIBLE_ONLY
}
