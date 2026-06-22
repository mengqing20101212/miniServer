package ly.logic.player.coroutine;

/** 记录当前执行流正在处理哪个玩家的串行队列。 */
public final class PlayerThreadContext {
    private static final ThreadLocal<Long> CURRENT_PLAYER_ID = new ThreadLocal<>();

    private PlayerThreadContext() {
    }

    public static long currentPlayerId() {
        Long playerId = CURRENT_PLAYER_ID.get();
        return playerId == null ? 0L : playerId;
    }

    public static void enter(long playerId) {
        CURRENT_PLAYER_ID.set(playerId);
    }

    public static void exit() {
        CURRENT_PLAYER_ID.remove();
    }
}
