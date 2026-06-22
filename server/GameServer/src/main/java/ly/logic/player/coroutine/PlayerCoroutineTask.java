package ly.logic.player.coroutine;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import ly.logic.player.Player;

/**
 * 投递到目标玩家队列中执行的协程调用任务。
 *
 * <p>调用线程持有 {@link #future()} 等待结果，目标玩家队列执行 {@link #execute(Player)}
 * 完成这个 future。任务完成、取消或超时后会释放业务 lambda，避免 lambda 捕获的玩家、模块等对象长期滞留。
 */
public class PlayerCoroutineTask<T> {
    /** 发起调用的玩家 id；非玩家线程发起时为 0，只用于日志和死锁检测。 */
    private final long sourcePlayerId;
    /** 目标玩家 id，任务必须在这个玩家自己的队列里执行。 */
    private final long targetPlayerId;
    /** 创建时间，后续排查队列堆积、超时问题时使用。 */
    private final long createTimeMillis;
    private final String description;
    private final CompletableFuture<T> future = new CompletableFuture<>();
    private final AtomicBoolean cancelled = new AtomicBoolean();
    /** 真正要在目标玩家上执行的业务逻辑。任务结束后会置空释放引用。 */
    private Function<Player, T> action;

    public PlayerCoroutineTask(
            long sourcePlayerId,
            long targetPlayerId,
            String description,
            Function<Player, T> action) {
        this.sourcePlayerId = sourcePlayerId;
        this.targetPlayerId = targetPlayerId;
        this.description = description;
        this.action = action;
        this.createTimeMillis = System.currentTimeMillis();
    }

    public void execute(Player target) {
        Function<Player, T> localAction = action;
        // 超时或批量取消后，任务可能仍然留在目标队列里；执行到这里时直接跳过。
        if (cancelled.get() || localAction == null) {
            release();
            return;
        }
        try {
            future.complete(localAction.apply(target));
        } catch (Throwable e) {
            future.completeExceptionally(e);
        } finally {
            release();
        }
    }

    public void cancel(Throwable cause) {
        if (cancelled.compareAndSet(false, true)) {
            // 唤醒正在等待 future 的调用线程，并把失败原因传回去。
            future.completeExceptionally(cause);
            release();
        }
    }

    public CompletableFuture<T> future() {
        return future;
    }

    public long getSourcePlayerId() {
        return sourcePlayerId;
    }

    public long getTargetPlayerId() {
        return targetPlayerId;
    }

    public long getCreateTimeMillis() {
        return createTimeMillis;
    }

    public String getDescription() {
        return description;
    }

    private void release() {
        // 任务完成或取消后立即释放业务 lambda 捕获的对象，避免玩家下线后被旧任务引用。
        action = null;
    }
}
