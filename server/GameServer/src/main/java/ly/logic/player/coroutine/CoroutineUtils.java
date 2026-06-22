package ly.logic.player.coroutine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;
import ly.LoggerDef;
import ly.logic.player.Player;
import ly.net.GamePlayer;

/** 玩家协程同步调用工具。 */
public final class CoroutineUtils {
    public static final long DEFAULT_TIMEOUT_MILLIS = 1_000L;

    private CoroutineUtils() {
    }

    public static Player on(Player target) {
        return on(target, DEFAULT_TIMEOUT_MILLIS);
    }

    public static Player on(Player target, long timeoutMillis) {
        validateTarget(target);
        return PlayerCoroutineProxyFactory.create(target, timeoutMillis);
    }

    public static TimeoutBuilder timeout(long timeoutMillis) {
        return new TimeoutBuilder(timeoutMillis);
    }

    public static CoroutineBatch batch(Collection<Player> players) {
        return new CoroutineBatch(players);
    }

    public static <T> T call(Player target, Function<Player, T> action) {
        return call(target, action, DEFAULT_TIMEOUT_MILLIS, "function");
    }

    public static <T> T call(Player target, Function<Player, T> action, long timeoutMillis) {
        return call(target, action, timeoutMillis, "function");
    }

    public static void run(Player target, Consumer<Player> action) {
        run(target, action, DEFAULT_TIMEOUT_MILLIS);
    }

    public static void run(Player target, Consumer<Player> action, long timeoutMillis) {
        call(
                target,
                player -> {
                    action.accept(player);
                    return null;
                },
                timeoutMillis,
                "consumer");
    }

    static Object invoke(Player target, Method method, Object[] args, long timeoutMillis) {
        Objects.requireNonNull(method, "method");
        return call(
                target,
                player -> invokeMethod(player, method, args),
                timeoutMillis,
                method.getDeclaringClass().getSimpleName() + "." + method.getName());
    }

    static <T> T call(Player target, Function<Player, T> action, long timeoutMillis, String description) {
        validateTarget(target);
        Objects.requireNonNull(action, "action");
        if (timeoutMillis <= 0) {
            throw new IllegalArgumentException("timeoutMillis must be positive");
        }

        long targetPlayerId = target.getPlayerId();
        long sourcePlayerId = PlayerThreadContext.currentPlayerId();
        if (sourcePlayerId == targetPlayerId) {
            return action.apply(target);
        }

        GamePlayer gamePlayer = target.getGamePlayer();
        if (gamePlayer == null) {
            throw new IllegalStateException("target player has no GamePlayer, playerId=" + targetPlayerId);
        }

        CoroutineWaitGraph waitGraph = CoroutineWaitGraph.getInstance();
        waitGraph.addEdge(sourcePlayerId, targetPlayerId);
        PlayerCoroutineTask<T> task =
                new PlayerCoroutineTask<>(sourcePlayerId, targetPlayerId, description, action);
        boolean submitted = false;
        try {
            submitted = gamePlayer.addCoroutineTask(task);
            if (!submitted) {
                throw new IllegalStateException(
                        "target player coroutine queue full, playerId="
                                + targetPlayerId
                                + ", queueSize="
                                + gamePlayer.getWorkQueueSize());
            }
            return task.future().get(timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            CoroutineTimeoutException timeout =
                    new CoroutineTimeoutException(
                            "player coroutine timeout, source="
                                    + sourcePlayerId
                                    + ", target="
                                    + targetPlayerId
                                    + ", desc="
                                    + description
                                    + ", timeoutMs="
                                    + timeoutMillis
                                    + ", targetQueueSize="
                                    + gamePlayer.getWorkQueueSize());
            task.cancel(timeout);
            LoggerDef.SystemLogger.error(timeout.getMessage());
            throw timeout;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            task.cancel(e);
            throw new IllegalStateException("player coroutine interrupted, target=" + targetPlayerId, e);
        } catch (ExecutionException e) {
            throw rethrow(e.getCause());
        } catch (RuntimeException e) {
            task.cancel(e);
            throw e;
        } finally {
            if (!submitted) {
                task.cancel(new IllegalStateException("player coroutine submit failed"));
            }
            waitGraph.removeEdge(sourcePlayerId, targetPlayerId);
        }
    }

    private static void validateTarget(Player target) {
        if (target == null) {
            throw new IllegalArgumentException("target player is null");
        }
        if (target.getPlayerData() == null) {
            throw new IllegalStateException("target player data is null");
        }
    }

    private static Object invokeMethod(Player target, Method method, Object[] args) {
        try {
            method.setAccessible(true);
            return method.invoke(target, args == null ? new Object[0] : args);
        } catch (InvocationTargetException e) {
            throw rethrow(e.getTargetException());
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("invoke player coroutine method failed: " + method, e);
        }
    }

    static RuntimeException rethrow(Throwable throwable) {
        if (throwable instanceof RuntimeException runtimeException) {
            return runtimeException;
        }
        if (throwable instanceof Error error) {
            throw error;
        }
        return new IllegalStateException(throwable);
    }

    public record TimeoutBuilder(long timeoutMillis) {
        public Player on(Player target) {
            return CoroutineUtils.on(target, timeoutMillis);
        }

        public CoroutineBatch batch(Collection<Player> players) {
            return CoroutineUtils.batch(players).timeout(timeoutMillis);
        }
    }
}
