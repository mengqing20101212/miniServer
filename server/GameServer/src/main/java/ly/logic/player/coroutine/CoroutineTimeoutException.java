package ly.logic.player.coroutine;

/** 玩家协程同步调用等待超时时抛出。 */
public class CoroutineTimeoutException extends RuntimeException {
    public CoroutineTimeoutException(String message) {
        super(message);
    }
}
