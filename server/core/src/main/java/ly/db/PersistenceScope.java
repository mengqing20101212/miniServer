package ly.db;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * A synchronous task segment. Exceptions also flush mutated state: this is not a rollback scope.
 */
public final class PersistenceScope implements AutoCloseable {
    private static final ThreadLocal<PersistenceScope> CURRENT = new ThreadLocal<>();
    private final PersistenceScope parent;
    private final Set<AbstractEntry> dirty = Collections.newSetFromMap(new IdentityHashMap<>());
    private final java.util.Map<Object, Runnable> preparations = new IdentityHashMap<>();
    private final java.util.Map<Object, Runnable> afterFlush = new java.util.LinkedHashMap<>();
    private boolean closed;
    private final ly.ConfigScope configScope = ly.ConfigScope.open();

    /** 同一保存区间按对象合并快照准备；无作用域时立即提交。 */
    public static void registerSnapshotPreparation(Object owner, Runnable preparation) {
        PersistenceScope scope = CURRENT.get();
        if (scope == null) {
            preparation.run();
        } else {
            scope.preparations.put(owner, preparation);
        }
    }

    public static void afterFlush(Runnable action) {
        afterFlush(new Object(), action);
    }

    public static void afterFlush(Object owner, Runnable action) {
        PersistenceScope scope = CURRENT.get();
        if (scope == null) action.run();
        else scope.afterFlush.put(owner, action);
    }

    private PersistenceScope() {
        parent = CURRENT.get();
        CURRENT.set(this);
    }

    public static PersistenceScope open() {
        return new PersistenceScope();
    }

    static void changed(AbstractEntry entry) {
        PersistenceScope scope = CURRENT.get();
        if (scope == null) {
            // Outside framework task boundaries durability takes precedence over coalescing.
            MysqlService.getInstance().update(entry);
        } else {
            scope.dirty.add(entry);
        }
    }

    static void accepted(AbstractEntry entry) {
        for (PersistenceScope scope = CURRENT.get(); scope != null; scope = scope.parent) {
            scope.dirty.remove(entry);
        }
    }

    public static void flushCurrent() {
        PersistenceScope scope = CURRENT.get();
        if (scope == null) {
            return;
        }
        flushParents(scope);
    }

    private static void flushParents(PersistenceScope scope) {
        if (scope.parent != null) {
            flushParents(scope.parent);
        }
        scope.flush();
    }

    private void flush() {
        for (Object owner : preparations.keySet().toArray()) {
            Runnable preparation = preparations.get(owner);
            preparation.run();
            preparations.remove(owner);
        }
        for (AbstractEntry entry : dirty.toArray(new AbstractEntry[0])) {
            if (entry.canSave()) {
                MysqlService.getInstance().update(entry);
            }
            dirty.remove(entry);
        }
        runAfterFlush();
    }

    private void runAfterFlush() {
        java.util.List<Runnable> actions = new java.util.ArrayList<>(afterFlush.values());
        afterFlush.clear();
        for (Runnable action : actions) action.run();
    }

    @Override
    public void close() {
        if (closed) {
            return;
        }
        try {
            if (parent == null) flush();
            else {
                parent.dirty.addAll(dirty);
                parent.preparations.putAll(preparations);
                parent.afterFlush.putAll(afterFlush);
            }
        } finally {
            closed = true;
            configScope.close();
            CURRENT.set(parent);
            if (parent == null) {
                CURRENT.remove();
            }
        }
    }
}
