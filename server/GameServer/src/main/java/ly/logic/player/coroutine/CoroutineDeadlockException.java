package ly.logic.player.coroutine;

/**
 * 玩家协程同步调用检测到等待环时抛出。
 *
 * <p>这是编程错误，表示当前调用链可能出现 A 等 B、B 等 A 的互相等待，需要调整业务调用方向或拆成异步事件。
 */
public class CoroutineDeadlockException extends RuntimeException {
    public CoroutineDeadlockException(String message) {
        super(message);
    }
}
