package ly.logic.player.coroutine;

/** 批量玩家协程调用中，单个目标失败后的处理决定。 */
public enum CoroutineFailureDecision {
    /** 继续等待其他目标玩家的执行结果。 */
    CONTINUE,
    /** 取消剩余等待，已投递但未执行的任务会在目标队列里自行跳过，已开始执行的任务自然结束。 */
    CANCEL_REMAINING,
    /** 立即把失败抛回当前调用方。 */
    THROW
}
