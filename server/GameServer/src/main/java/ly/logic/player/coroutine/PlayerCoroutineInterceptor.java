package ly.logic.player.coroutine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

/** ByteBuddy 代理方法拦截器，把 Player 方法调用转成目标玩家队列任务。 */
public class PlayerCoroutineInterceptor {
    private final ly.logic.player.Player target;
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
        if (method.getDeclaringClass() == Object.class) {
            return invokeObjectMethod(method, args, superCall);
        }
        return CoroutineUtils.invoke(target, method, args, timeoutMillis);
    }

    private Object invokeObjectMethod(Method method, Object[] args, Callable<?> superCall) throws Throwable {
        String name = method.getName();
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
