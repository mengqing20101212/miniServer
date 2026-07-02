package ly.logic.player.coroutine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;
import ly.logic.player.Player;
import ly.net.GamePlayer;

/**
 * 批量玩家协程调用构建器。
 *
 * <p>用于当前业务一次性向多个玩家队列投递同一段逻辑，并等待所有玩家返回。典型场景是队伍、房间、
 * 公会这类跨玩家聚合逻辑。调用方可以通过 {@link #onFailure(CoroutineFailureHandler)}
 * 决定单个玩家失败后继续等、取消剩余任务，还是直接抛异常。
 */
public class CoroutineBatch {
    /** 目标玩家快照，构造时复制一份，避免调用方集合后续变化影响本次批量调用。 */
    private final List<Player> players;
    private long timeoutMillis = CoroutineUtils.DEFAULT_TIMEOUT_MILLIS;
    private CoroutineFailureHandler failureHandler =
            (player, error, context) -> CoroutineFailureDecision.THROW;

    CoroutineBatch(Collection<Player> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("players is empty");
        }
        this.players = new ArrayList<>(players);
    }

    public CoroutineBatch timeout(long timeoutMillis) {
        if (timeoutMillis <= 0) {
            throw new IllegalArgumentException("timeoutMillis must be positive");
        }
        this.timeoutMillis = timeoutMillis;
        return this;
    }

    /** 设置单个目标失败后的处理策略，不设置时默认直接把失败抛给调用方。 */
    public CoroutineBatch onFailure(CoroutineFailureHandler failureHandler) {
        this.failureHandler = Objects.requireNonNull(failureHandler, "failureHandler");
        return this;
    }

    /**
     * 向所有目标玩家队列投递有返回值任务，并等待结果。
     *
     * <p>返回值中同时保存成功和失败，除非失败策略选择 {@code THROW} 导致方法提前抛出。
     */
    public <T> CoroutineBatchResult<T> call(Function<Player, T> action) {
        Objects.requireNonNull(action, "action");
        long sourcePlayerId = PlayerThreadContext.currentPlayerId();
        CoroutineWaitGraph waitGraph = CoroutineWaitGraph.getInstance();
        // 批量调用也要一次性登记等待关系，先检查是否会形成等待环。
        waitGraph.addEdges(sourcePlayerId, players);

        List<PlayerCoroutineTask<T>> tasks = new ArrayList<>();
        CoroutineBatchResult<T> result = new CoroutineBatchResult<>();
        long deadline = System.currentTimeMillis() + timeoutMillis;
        try {
            submitAll(action, sourcePlayerId, tasks, result);
            waitAll(tasks, result, deadline, sourcePlayerId);
            return result;
        } finally {
            waitGraph.removeEdges(sourcePlayerId, players);
        }
    }

    public CoroutineBatchResult<Void> run(Consumer<Player> action) {
        Objects.requireNonNull(action, "action");
        return call(
                player -> {
                    action.accept(player);
                    return null;
                });
    }

    private <T> void submitAll(
            Function<Player, T> action,
            long sourcePlayerId,
            List<PlayerCoroutineTask<T>> tasks,
            CoroutineBatchResult<T> result) {
        for (Player player : players) {
            validatePlayer(player);
            long targetPlayerId = player.getPlayerId();
            // 当前玩家调用自己时直接执行，不投递到队列，避免自己等自己。
            if (sourcePlayerId == targetPlayerId) {
                result.addSuccess(targetPlayerId, action.apply(player));
                continue;
            }

            GamePlayer gamePlayer = player.getGamePlayer();
            PlayerCoroutineTask<T> task =
                    new PlayerCoroutineTask<>(sourcePlayerId, targetPlayerId, "batch", action);
            if (gamePlayer == null || !gamePlayer.addCoroutineTask(task)) {
                IllegalStateException error =
                        new IllegalStateException("submit player coroutine batch failed, playerId=" + targetPlayerId);
                task.cancel(error);
                result.addFailure(targetPlayerId, error);
                continue;
            }
            // 已成功进入目标队列，后续统一在 waitAll 中等待 future。
            tasks.add(task);
        }
    }

    private <T> void waitAll(
            List<PlayerCoroutineTask<T>> tasks,
            CoroutineBatchResult<T> result,
            long deadline,
            long sourcePlayerId) {
        int successCount = result.getSuccesses().size();
        int failureCount = result.getFailures().size();
        for (PlayerCoroutineTask<T> task : tasks) {
            long waitMillis = deadline - System.currentTimeMillis();
            try {
                if (waitMillis <= 0) {
                    throw new TimeoutException("batch timeout");
                }
                T value = task.future().get(waitMillis, TimeUnit.MILLISECONDS);
                result.addSuccess(task.getTargetPlayerId(), value);
                successCount++;
            } catch (TimeoutException e) {
                CoroutineTimeoutException timeout =
                        new CoroutineTimeoutException(
                                "player coroutine batch timeout, source="
                                        + sourcePlayerId
                                        + ", target="
                                        + task.getTargetPlayerId()
                                        + ", timeoutMs="
                                        + timeoutMillis);
                task.cancel(timeout);
                result.addFailure(task.getTargetPlayerId(), timeout);
                failureCount++;
                if (handleFailure(task, timeout, successCount, failureCount) != CoroutineFailureDecision.CONTINUE) {
                    // 调用方不想继续等时，取消尚未完成的任务；已开始执行的任务由目标队列自然结束。
                    cancelRemain(tasks, timeout);
                    return;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                task.cancel(e);
                throw new IllegalStateException("player coroutine batch interrupted", e);
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                result.addFailure(task.getTargetPlayerId(), cause);
                failureCount++;
                CoroutineFailureDecision decision = handleFailure(task, cause, successCount, failureCount);
                if (decision == CoroutineFailureDecision.THROW) {
                    cancelRemain(tasks, cause);
                    throw CoroutineUtils.rethrow(cause);
                }
                if (decision == CoroutineFailureDecision.CANCEL_REMAINING) {
                    cancelRemain(tasks, cause);
                    return;
                }
            } catch (CancellationException e) {
                result.addFailure(task.getTargetPlayerId(), e);
                failureCount++;
            }
        }
    }

    private CoroutineFailureDecision handleFailure(
            PlayerCoroutineTask<?> task,
            Throwable error,
            int successCount,
            int failureCount) {
        Player player = findPlayer(task.getTargetPlayerId());
        CoroutineBatchContext context =
                new CoroutineBatchContext(
                        task.getSourcePlayerId(), players.size(), successCount, failureCount, timeoutMillis);
        CoroutineFailureDecision decision = failureHandler.onFailure(player, error, context);
        // 回调返回 null 视为保守处理：直接抛异常，避免静默吞掉业务错误。
        return decision == null ? CoroutineFailureDecision.THROW : decision;
    }

    private Player findPlayer(long playerId) {
        for (Player player : players) {
            if (player != null && player.getPlayerId() == playerId) {
                return player;
            }
        }
        return null;
    }

    private void cancelRemain(List<? extends PlayerCoroutineTask<?>> tasks, Throwable cause) {
        for (PlayerCoroutineTask<?> task : tasks) {
            if (!task.future().isDone()) {
                // 这里只是标记 future 失败并释放引用；任务如果已经在目标队列里，执行时会看到取消标记并跳过。
                task.cancel(cause);
            }
        }
    }

    private void validatePlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("batch player is null");
        }
        if (player.getPlayerData() == null) {
            throw new IllegalStateException("batch player data is null");
        }
    }
}
