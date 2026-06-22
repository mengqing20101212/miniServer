package ly.logic.player.coroutine;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import ly.logic.player.Player;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

/** 创建 Player 协程代理对象。 */
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
                        .subclass(Player.class)
                        .name(Player.class.getName() + "$CoroutineProxy")
                        .defineField(INTERCEPTOR_FIELD, PlayerCoroutineInterceptor.class, Modifier.PRIVATE)
                        .method(
                                ElementMatchers.isVirtual()
                                        .and(ElementMatchers.not(ElementMatchers.isFinal()))
                                        .and(ElementMatchers.not(ElementMatchers.isStatic()))
                                        .and(ElementMatchers.not(ElementMatchers.isDeclaredBy(Object.class))))
                        .intercept(MethodDelegation.toField(INTERCEPTOR_FIELD))
                        .make()
                        .load(Player.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                        .getLoaded();
    }
}
