package ly.logic.player.coroutine;

/**
 * 记录当前执行流正在处理哪个玩家的串行队列。
 *
 * <p>这里只保存 playerId，不保存 Player/GamePlayer 引用，避免玩家下线、重登或协程迁移后被 ThreadLocal 持有。
 * GamePlayer 每执行一个 work item 前调用 {@link #enter(long)}，执行结束必须调用 {@link #exit()}。
 */
public final class PlayerThreadContext {
    private static final ThreadLocal<Long> CURRENT_PLAYER_ID = new ThreadLocal<>();

    private PlayerThreadContext() {
    }

    public static long currentPlayerId() {
        Long playerId = CURRENT_PLAYER_ID.get();
        return playerId == null ? 0L : playerId;
    }

    /** 标记当前线程正在执行指定玩家的队列任务。 */
    public static void enter(long playerId) {
        CURRENT_PLAYER_ID.set(playerId);
    }

    /** 清理当前线程的玩家上下文，必须放在 finally 中调用。 */
    public static void exit() {
        CURRENT_PLAYER_ID.remove();
    }
}
