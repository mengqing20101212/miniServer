package ly.logic.player.coroutine;

/**
 * 批量玩家协程调用失败处理时的上下文。
 *
 * @param sourcePlayerId 发起批量调用的玩家 id，非玩家线程发起时为 0
 * @param totalCount 本次批量调用的目标玩家总数
 * @param successCount 触发当前失败回调前已经成功的数量
 * @param failureCount 包含当前失败在内的失败数量
 * @param timeoutMillis 本次批量调用设置的总超时时间
 */
public record CoroutineBatchContext(
        long sourcePlayerId,
        int totalCount,
        int successCount,
        int failureCount,
        long timeoutMillis) {
}
