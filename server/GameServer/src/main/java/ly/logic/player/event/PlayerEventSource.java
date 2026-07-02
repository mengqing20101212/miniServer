package ly.logic.player.event;

/**
 * 玩家事件来源。
 *
 * <p>事件最终都会进入目标玩家自己的执行队列，这个来源只用于业务判断和日志排查。
 */
public enum PlayerEventSource {
    /** 玩家自身行为产生的事件。 */
    SELF,
    /** 其他玩家行为影响当前玩家时产生的事件。 */
    OTHER_PLAYER,
    /** 其他协程或业务模块投递给玩家的事件。 */
    MODULE,
    /** 系统全局事件广播到玩家后产生的事件。 */
    SYSTEM_GLOBAL
}
