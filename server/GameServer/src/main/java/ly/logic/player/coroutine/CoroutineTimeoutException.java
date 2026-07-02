package ly.logic.player.coroutine;

/**
 * 玩家协程同步调用等待超时时抛出。
 *
 * <p>超时后调用方会被唤醒，目标队列里尚未执行的任务会在之后被跳过；如果任务已经开始执行，
 * 不能强行中断，只能等待目标队列自然完成。
 */
public class CoroutineTimeoutException extends RuntimeException {
    public CoroutineTimeoutException(String message) {
        super(message);
    }
}
