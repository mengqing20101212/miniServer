package ly.logic.player.coroutine;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import ly.logic.player.Player;

/** 投递到目标玩家队列中执行的协程调用任务。 */
public class PlayerCoroutineTask<T> {
    private final long sourcePlayerId;
    private final long targetPlayerId;
    private final long createTimeMillis;
    private final String description;
    private final CompletableFuture<T> future = new CompletableFuture<>();
    private final AtomicBoolean cancelled = new AtomicBoolean();
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
