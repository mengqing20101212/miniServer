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

/**
 * 玩家协程同步调用入口。
 *
 * <p>这个工具解决的是“当前玩家线程需要读取或修改另一个玩家对象”的问题。调用方会阻塞等待结果，
 * 但真正的业务代码会被投递到目标玩家自己的 {@code GamePlayer} 队列里执行，从而保证目标玩家对象仍然只被
 * 自己的串行队列访问。
 *
 * <p>常用写法：
 *
 * <pre>{@code
 * int level = CoroutineUtils.on(targetPlayer).getLevel();
 * CoroutineUtils.run(targetPlayer, player -> player.addExp(10));
 * }</pre>
 */
public final class CoroutineUtils {
    /** 默认等待时间。业务调用如果可能跨服、跨模块耗时较长，需要显式指定更大的超时时间。 */
    public static final long DEFAULT_TIMEOUT_MILLIS = 1_000L;

    private CoroutineUtils() {
    }

    public static Player on(Player target) {
        return on(target, DEFAULT_TIMEOUT_MILLIS);
    }

    /**
     * 返回目标玩家的代理对象。
     *
     * <p>后续在代理对象上调用的 Player 方法不会直接执行，而是进入
     * {@link PlayerCoroutineInterceptor}，再转成 {@link PlayerCoroutineTask} 投递到目标玩家队列。
     */
    public static Player on(Player target, long timeoutMillis) {
        validateTarget(target);
        return PlayerCoroutineProxyFactory.create(target, timeoutMillis);
    }

    /** 创建带自定义超时时间的调用构建器，支持 {@code CoroutineUtils.timeout(3000).on(player).getLevel()}。 */
    public static TimeoutBuilder timeout(long timeoutMillis) {
        return new TimeoutBuilder(timeoutMillis);
    }

    /** 创建批量调用构建器，用于一次等待多个目标玩家的执行结果。 */
    public static CoroutineBatch batch(Collection<Player> players) {
        return new CoroutineBatch(players);
    }

    /** 直接用 lambda 在目标玩家队列中执行，并返回结果。 */
    public static <T> T call(Player target, Function<Player, T> action) {
        return call(target, action, DEFAULT_TIMEOUT_MILLIS, "function");
    }

    /** 直接用 lambda 在目标玩家队列中执行，并使用自定义超时时间。 */
    public static <T> T call(Player target, Function<Player, T> action, long timeoutMillis) {
        return call(target, action, timeoutMillis, "function");
    }

    /** 直接用 lambda 在目标玩家队列中执行，无返回值。 */
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
        // 同一个玩家队列里调用自己不需要投递任务，直接执行可以避免无意义阻塞。
        if (sourcePlayerId == targetPlayerId) {
            return action.apply(target);
        }

        GamePlayer gamePlayer = target.getGamePlayer();
        if (gamePlayer == null) {
            throw new IllegalStateException("target player has no GamePlayer, playerId=" + targetPlayerId);
        }

        CoroutineWaitGraph waitGraph = CoroutineWaitGraph.getInstance();
        // 先登记等待边，再投递任务。这里会提前发现 A 等 B、B 又等 A 这种死锁。
        waitGraph.addEdge(sourcePlayerId, targetPlayerId);
        PlayerCoroutineTask<T> task = new PlayerCoroutineTask<>(sourcePlayerId, targetPlayerId, description, action);
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
            // 当前线程在这里阻塞，直到目标玩家队列执行完任务、超时、或被取消。
            return task.future().get(timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            CoroutineTimeoutException timeout = new CoroutineTimeoutException(
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
            // 等待关系只描述当前这次阻塞调用，结束后必须移除，避免误判后续调用死锁。
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

    /** 指定超时时间后的链式调用对象。 */
    public record TimeoutBuilder(long timeoutMillis) {
        public Player on(Player target) {
            return CoroutineUtils.on(target, timeoutMillis);
        }

        public CoroutineBatch batch(Collection<Player> players) {
            return CoroutineUtils.batch(players).timeout(timeoutMillis);
        }
    }
}
