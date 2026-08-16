package ly.logic.player.persistence;

import java.util.List;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import ly.LoggerDef;
import ly.db.entry.PlayerEntry;
import ly.db.entry.PlayerModuleEntry;
import ly.logic.player.PlayerData;

/** 异步保存玩家模块快照，并对失败批次做有限退避重试。 */
public final class PlayerModulePersistenceService {
    private static final int MAX_PENDING_TASKS = 10_000;
    private static final int MAX_RETRY_COUNT = 5;
    private static final int WORKER_COUNT = Math.max(2, Math.min(4, Runtime.getRuntime().availableProcessors()));
    private static final long INITIAL_RETRY_DELAY_MILLIS = 100L;
    private static final PlayerModulePersistenceService INSTANCE =
            new PlayerModulePersistenceService(new MysqlPlayerModuleStore(), true);

    private final PlayerModuleStore store;
    private final DelayQueue<PersistTask> queue = new DelayQueue<>();
    private final AtomicInteger pendingTasks = new AtomicInteger();
    private final AtomicBoolean accepting = new AtomicBoolean(true);
    private final List<Thread> workers;

    public static PlayerModulePersistenceService getInstance() {
        return INSTANCE;
    }

    public PlayerModulePersistenceService(PlayerModuleStore store, boolean startWorker) {
        if (store == null) {
            throw new IllegalArgumentException("store cannot be null");
        }
        this.store = store;
        if (startWorker) {
            workers = java.util.stream.IntStream.range(0, WORKER_COUNT)
                    .mapToObj(index -> Thread.ofVirtual()
                            .name("player-module-persistence-" + index)
                            .start(this::runWorker))
                    .toList();
        } else {
            workers = List.of();
        }
    }

    public Map<Integer, PlayerModuleEntry> load(long playerId) {
        return store.load(playerId);
    }

    public boolean clearLegacyModuleData(PlayerEntry playerEntry) {
        return store.clearLegacyModuleData(playerEntry);
    }

    public boolean submit(PlayerData owner, List<PlayerModuleEntry> snapshots) {
        if (!accepting.get() || owner == null || snapshots == null || snapshots.isEmpty()) {
            return false;
        }
        int pending = pendingTasks.incrementAndGet();
        if (pending > MAX_PENDING_TASKS) {
            pendingTasks.decrementAndGet();
            LoggerDef.DbLogger.error("player module persistence queue full, playerId={}, pending={}", owner.getPlayerId(), pending);
            return false;
        }
        queue.offer(new PersistTask(owner, snapshots));
        return true;
    }

    /** 同步执行一次，供测试和受控停服刷新使用。 */
    public boolean persistNow(PlayerData owner, List<PlayerModuleEntry> snapshots) {
        if (owner == null || snapshots == null || snapshots.isEmpty()) {
            return true;
        }
        boolean success = store.saveBatch(owner.getPlayerId(), snapshots);
        if (success) {
            owner.markModulesPersisted(snapshots);
        } else {
            owner.releaseSubmittedModules(snapshots);
        }
        return success;
    }

    public void shutdown(long timeoutMillis) {
        accepting.set(false);
        long deadline = System.currentTimeMillis() + Math.max(0, timeoutMillis);
        while (pendingTasks.get() > 0 && System.currentTimeMillis() < deadline) {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        workers.forEach(Thread::interrupt);
        if (pendingTasks.get() > 0) {
            LoggerDef.DbLogger.error("player module persistence shutdown with pending tasks={}", pendingTasks.get());
        }
    }

    private void runWorker() {
        while (!Thread.currentThread().isInterrupted()) {
            PersistTask task = null;
            try {
                task = queue.take();
                boolean success = store.saveBatch(task.owner.getPlayerId(), task.snapshots);
                if (success) {
                    task.owner.markModulesPersisted(task.snapshots);
                    pendingTasks.decrementAndGet();
                    task.owner.flushAsync();
                    continue;
                }
                retryOrRelease(task, null);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                if (task == null) {
                    LoggerDef.DbLogger.error("player module persistence worker error", e);
                } else {
                    retryOrRelease(task, e);
                }
            }
        }
    }

    private void retryOrRelease(PersistTask task, Exception error) {
        task.retryCount++;
        if (task.retryCount > MAX_RETRY_COUNT || !accepting.get()) {
            pendingTasks.decrementAndGet();
            task.owner.releaseSubmittedModules(task.snapshots);
            LoggerDef.DbLogger.error(
                    "player module persistence failed permanently, playerId={}, modules={}, retries={}",
                    task.owner.getPlayerId(),
                    task.snapshots.size(),
                    task.retryCount,
                    error);
            return;
        }
        long delay = INITIAL_RETRY_DELAY_MILLIS << Math.min(task.retryCount - 1, 10);
        task.nextAttemptAt = System.currentTimeMillis() + delay;
        queue.offer(task);
    }

    private static final class PersistTask implements Delayed {
        private final PlayerData owner;
        private final List<PlayerModuleEntry> snapshots;
        private int retryCount;
        private long nextAttemptAt = System.currentTimeMillis();

        private PersistTask(PlayerData owner, List<PlayerModuleEntry> snapshots) {
            this.owner = owner;
            this.snapshots = snapshots.stream().map(PlayerModuleEntry::snapshot).toList();
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(nextAttemptAt - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed other) {
            long difference = getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS);
            return Long.compare(difference, 0L);
        }
    }
}
