package ly.logic.player.coroutine;

/** 批量玩家协程调用失败处理时的上下文。 */
public record CoroutineBatchContext(
        long sourcePlayerId,
        int totalCount,
        int successCount,
        int failureCount,
        long timeoutMillis) {
}
