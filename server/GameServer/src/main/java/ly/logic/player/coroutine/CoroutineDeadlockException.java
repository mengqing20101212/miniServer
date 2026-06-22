package ly.logic.player.coroutine;

/** 玩家协程同步调用检测到等待环时抛出。 */
public class CoroutineDeadlockException extends RuntimeException {
    public CoroutineDeadlockException(String message) {
        super(message);
    }
}
