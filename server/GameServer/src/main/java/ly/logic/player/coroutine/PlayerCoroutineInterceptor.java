package ly.logic.player.coroutine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

/**
 * ByteBuddy 代理方法拦截器，把 Player 方法调用转成目标玩家队列任务。
 *
 * <p>这个对象被注入到动态生成的 Player 代理类中。调用 {@code proxy.getLevel()} 时，
 * ByteBuddy 会把方法、参数传进 {@link #intercept(Method, Object[], Callable)}，
 * 这里再交给 {@link CoroutineUtils#invoke(ly.logic.player.Player, Method, Object[], long)}
 * 投递到目标玩家队列。
 */
public class PlayerCoroutineInterceptor {
    /** 被代理的真实目标玩家。 */
    private final ly.logic.player.Player target;
    /** 当前代理对象上每次方法调用的最长等待时间。 */
    private final long timeoutMillis;

    public PlayerCoroutineInterceptor(ly.logic.player.Player target, long timeoutMillis) {
        this.target = target;
        this.timeoutMillis = timeoutMillis;
    }

    @RuntimeType
    public Object intercept(
            @Origin Method method,
            @AllArguments Object[] args,
            @SuperCall(nullIfImpossible = true) Callable<?> superCall)
            throws Throwable {
        // Object 方法不走玩家队列，否则日志、集合比较等基础行为会被不必要地阻塞。
        if (method.getDeclaringClass() == Object.class) {
            return invokeObjectMethod(method, args, superCall);
        }
        return CoroutineUtils.invoke(target, method, args, timeoutMillis);
    }

    private Object invokeObjectMethod(Method method, Object[] args, Callable<?> superCall) throws Throwable {
        String name = method.getName();
        // 这些 Object 方法给出稳定语义：代理对象代表 target，但不真的等于 target 之外的对象。
        if ("toString".equals(name)) {
            return "CoroutineProxy{" + target + '}';
        }
        if ("hashCode".equals(name)) {
            return System.identityHashCode(target);
        }
        if ("equals".equals(name)) {
            return args != null && args.length == 1 && args[0] == target;
        }
        if (superCall != null) {
            try {
                return superCall.call();
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
        throw new UnsupportedOperationException("unsupported Object method: " + name + Arrays.toString(args));
    }
}
