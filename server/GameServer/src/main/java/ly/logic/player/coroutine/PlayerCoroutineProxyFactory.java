package ly.logic.player.coroutine;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import ly.logic.player.Player;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * 创建 Player 协程代理对象。
 *
 * <p>调用方写 {@code CoroutineUtils.on(playerB).getLevel()} 时，拿到的不是 playerB 本身，
 * 而是这里动态生成的 Player 子类。这个子类会拦截 Player 的业务方法，把方法调用投递到
 * playerB 自己的 GamePlayer 队列里执行，然后让当前调用线程等待结果。
 */
final class PlayerCoroutineProxyFactory {
    private static final String INTERCEPTOR_FIELD = "__coroutineInterceptor";
    private static final Class<? extends Player> PROXY_CLASS = createProxyClass();

    private PlayerCoroutineProxyFactory() {
    }

    static Player create(Player target, long timeoutMillis) {
        try {
            Player proxy = PROXY_CLASS.getDeclaredConstructor().newInstance();
            Field field = PROXY_CLASS.getDeclaredField(INTERCEPTOR_FIELD);
            field.setAccessible(true);
            field.set(proxy, new PlayerCoroutineInterceptor(target, timeoutMillis));
            return proxy;
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("create player coroutine proxy failed", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends Player> createProxyClass() {
        return (Class<? extends Player>)
                new ByteBuddy()
                        // 生成一个 Player 的子类，所以返回值仍然可以当 Player 使用。
                        .subclass(Player.class)
                        // 固定类名，避免每次创建代理对象都生成一个新的 Class。
                        .name(Player.class.getName() + "$CoroutineProxy")
                        // 每个代理对象持有自己的拦截器，拦截器里保存目标玩家和超时时间。
                        .defineField(INTERCEPTOR_FIELD, PlayerCoroutineInterceptor.class, Modifier.PRIVATE)
                        .method(
                                // 只拦截 Player 的普通虚方法。final/static/Object 方法不拦截，
                                // 避免影响 JVM 基础行为，也避免拦截不了 final 方法导致误判。
                                ElementMatchers.isVirtual()
                                        .and(ElementMatchers.not(ElementMatchers.isFinal()))
                                        .and(ElementMatchers.not(ElementMatchers.isStatic()))
                                        .and(ElementMatchers.not(ElementMatchers.isDeclaredBy(Object.class))))
                        // 被拦截的方法统一委托给 __coroutineInterceptor。
                        // 例如 proxy.getLevel() 实际会进入 PlayerCoroutineInterceptor.intercept(...)。
                        .intercept(MethodDelegation.toField(INTERCEPTOR_FIELD))
                        // 生成字节码。
                        .make()
                        // 把生成的 Class 注入到 Player 所在的 ClassLoader，保证能访问同一套业务类。
                        .load(Player.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                        // 返回生成好的代理 Class，后续 create(...) 只需要 newInstance 即可。
                        .getLoaded();
    }
}
