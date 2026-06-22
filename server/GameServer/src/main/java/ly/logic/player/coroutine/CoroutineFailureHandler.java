package ly.logic.player.coroutine;

import ly.logic.player.Player;

/** 批量玩家协程调用的失败处理回调。 */
@FunctionalInterface
public interface CoroutineFailureHandler {
    CoroutineFailureDecision onFailure(Player player, Throwable error, CoroutineBatchContext context);
}
