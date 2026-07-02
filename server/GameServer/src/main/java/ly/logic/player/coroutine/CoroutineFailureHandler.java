package ly.logic.player.coroutine;

import ly.logic.player.Player;

/**
 * 批量玩家协程调用的失败处理回调。
 *
 * <p>每个目标玩家失败时都会调用一次，业务可以根据失败玩家、异常类型、已成功/失败数量决定后续策略。
 */
@FunctionalInterface
public interface CoroutineFailureHandler {
    /**
     * @param player 失败的目标玩家；如果目标列表里已经找不到该玩家，可能为 null
     * @param error 目标任务抛出的异常、等待超时或取消原因
     * @param context 本次批量调用的统计上下文
     * @return 后续处理策略；返回 null 会按 THROW 处理
     */
    CoroutineFailureDecision onFailure(Player player, Throwable error, CoroutineBatchContext context);
}
