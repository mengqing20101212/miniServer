package ly;

/** 一个实际业务执行段固定同一快照。嵌套调用继承外层，不跨线程传递。 */
public final class ConfigScope implements AutoCloseable {
    private static final ThreadLocal<ConfigSnapshot> CURRENT = new ThreadLocal<>();
    private final ConfigSnapshot previous;
    private boolean closed;

    private ConfigScope(ConfigSnapshot snapshot) {
        previous = CURRENT.get();
        if (previous == null && snapshot != null) CURRENT.set(snapshot);
    }

    public static ConfigScope open() {
        return new ConfigScope(ConfigService.captureSnapshot());
    }

    public static ConfigScope use(ConfigSnapshot snapshot) {
        ConfigScope scope = new ConfigScope(snapshot);
        if (snapshot == null) CURRENT.remove();
        else CURRENT.set(snapshot);
        return scope;
    }

    public static ConfigSnapshot capture() {
        ConfigSnapshot snapshot = CURRENT.get();
        return snapshot == null ? ConfigService.captureSnapshot() : snapshot;
    }

    static ConfigSnapshot current() {
        return CURRENT.get();
    }

    public void close() {
        if (closed) return;
        closed = true;
        if (previous == null) CURRENT.remove();
        else CURRENT.set(previous);
    }
}
