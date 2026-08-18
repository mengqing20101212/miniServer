package ly.sceneserver.common.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import ly.LoggerDef;
import ly.sceneserver.common.SceneRuntime;

/**
 * 玩家场景投影异步入库服务。
 *
 * <p>同一个 playerId 永远进入同一个 FIFO 分区。失败任务在原地重试，因此后续版本不会越过
 * 旧版本；数据库 UPSERT 再用 revision 拒绝旧快照，抵抗重复投递和进程重启重放。
 */
public final class ScenePlayerPersistenceService implements AutoCloseable {
    private static final int DEFAULT_PARTITIONS = 4;
    private static final int DEFAULT_QUEUE_CAPACITY = 5_000;
    private static final int DEFAULT_MAX_RETRIES = 20;
    private static final long DEFAULT_INITIAL_RETRY_MILLIS = 200L;
    private static final long MAX_RETRY_MILLIS = 60_000L;

    private final PlayerSceneStore store;
    private final List<ArrayBlockingQueue<PersistTask>> queues;
    private final List<Thread> workers;
    private final AtomicBoolean accepting = new AtomicBoolean(true);
    /** 已被服务接受、但 Future 尚未完成的任务数，包含排队和正在执行的任务。 */
    private final AtomicInteger outstandingTasks = new AtomicInteger();
    private final AtomicBoolean[] partitionHealthy;
    private final int maxRetries;
    private final long initialRetryMillis;

    public ScenePlayerPersistenceService(PlayerSceneStore store) {
        this(
                store,
                Math.max(1, Integer.getInteger("slg.scene.persistence.partitions", DEFAULT_PARTITIONS)),
                Math.max(1, Integer.getInteger("slg.scene.persistence.queue-capacity", DEFAULT_QUEUE_CAPACITY)),
                Math.max(0, Integer.getInteger("slg.scene.persistence.max-retries", DEFAULT_MAX_RETRIES)),
                Math.max(1L, Long.getLong(
                        "slg.scene.persistence.initial-retry-millis",
                        DEFAULT_INITIAL_RETRY_MILLIS)));
    }

    ScenePlayerPersistenceService(
            PlayerSceneStore store,
            int partitionCount,
            int queueCapacity,
            int maxRetries,
            long initialRetryMillis) {
        if (store == null || partitionCount <= 0 || queueCapacity <= 0
                || maxRetries < 0 || initialRetryMillis <= 0L) {
            throw new IllegalArgumentException("invalid scene persistence parameters");
        }
        this.store = store;
        this.maxRetries = maxRetries;
        this.initialRetryMillis = initialRetryMillis;
        this.queues = new ArrayList<>(partitionCount);
        this.partitionHealthy = new AtomicBoolean[partitionCount];
        this.workers = new ArrayList<>(partitionCount);
        for (int i = 0; i < partitionCount; i++) {
            queues.add(new ArrayBlockingQueue<>(queueCapacity));
            partitionHealthy[i] = new AtomicBoolean(true);
            int partition = i;
            workers.add(Thread.ofVirtual()
                    .name("scene-player-persist-" + partition)
                    .start(() -> runWorker(partition)));
        }
    }

    /**
     * 提交不可变投影；Future 成功才表示数据库已经接受该 revision，可据此清理内存脏状态。
     */
    public CompletableFuture<Void> submit(PlayerSceneProjection projection) {
        CompletableFuture<Void> completion = new CompletableFuture<>();
        if (projection == null) {
            completion.completeExceptionally(new IllegalArgumentException("projection cannot be null"));
            return completion;
        }
        int partition = Math.floorMod(Long.hashCode(projection.playerId()), queues.size());
        if (!accepting.get()) {
            completion.completeExceptionally(new IllegalStateException("scene persistence is shutting down"));
            return completion;
        }
        if (!partitionHealthy[partition].get()) {
            completion.completeExceptionally(new IllegalStateException(
                    "scene persistence partition is halted: " + partition));
            return completion;
        }
        PersistTask task = new PersistTask(projection, completion);
        outstandingTasks.incrementAndGet();
        if (!queues.get(partition).offer(task)) {
            completeTask(task, new IllegalStateException(
                    "scene persistence queue is full: " + partition));
            LoggerDef.DbLogger.error(
                    "scene player persistence queue full, partition={}, playerId={}, revision={}",
                    partition, projection.playerId(), projection.revision());
        }
        return completion;
    }

    /**
     * 先在各 SceneShard 队列中取得该玩家最新个人迷雾，再提交完整投影。
     *
     * <p>调用方必须给 metadata 分配单调递增 revision；返回成功前不能把该版本标记为已落库。
     */
    public CompletableFuture<Void> snapshotAndSubmit(
            SceneRuntime.SceneInstance scene,
            PlayerSceneProjection metadata) {
        if (scene == null || metadata == null
                || !scene.config().sceneId().equals(metadata.sceneId())) {
            return CompletableFuture.failedFuture(
                    new IllegalArgumentException("scene does not match player projection"));
        }
        return scene.discoveredBlocksSnapshotAsync(metadata.playerId())
                .thenCompose(fog -> submit(metadata.withDiscoveredBlocks(fog)));
    }

    public int pendingTasks() {
        return outstandingTasks.get();
    }

    public boolean isHealthy() {
        for (AtomicBoolean healthy : partitionHealthy) {
            if (!healthy.get()) {
                return false;
            }
        }
        return true;
    }

    /** 停服时等待队列尽量清空；超时后剩余 Future 会失败，不能静默丢弃。 */
    public void shutdown(long timeoutMillis) {
        accepting.set(false);
        long deadline = System.currentTimeMillis() + Math.max(0L, timeoutMillis);
        while (pendingTasks() > 0 && System.currentTimeMillis() < deadline) {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException error) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        workers.forEach(Thread::interrupt);
        IllegalStateException error = new IllegalStateException("scene persistence stopped before task completed");
        for (ArrayBlockingQueue<PersistTask> queue : queues) {
            PersistTask task;
            while ((task = queue.poll()) != null) {
                completeTask(task, error);
            }
        }
    }

    @Override
    public void close() {
        shutdown(5_000L);
    }

    private void runWorker(int partition) {
        ArrayBlockingQueue<PersistTask> queue = queues.get(partition);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                PersistTask task = queue.poll(1L, TimeUnit.SECONDS);
                if (task == null) {
                    if (!accepting.get() && queue.isEmpty()) {
                        return;
                    }
                    continue;
                }
                if (!persistInOrder(partition, task)) {
                    return;
                }
            } catch (InterruptedException error) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /** 永久失败时停止整个分区，防止后续 revision 越过失败版本继续入库。 */
    private boolean persistInOrder(int partition, PersistTask task) {
        Throwable lastError = null;
        for (int attempt = 0; attempt <= maxRetries; attempt++) {
            try {
                store.upsert(task.projection());
                completeTask(task, null);
                return true;
            } catch (Throwable error) {
                lastError = error;
                if (attempt < maxRetries) {
                    if (!sleepBeforeRetry(attempt)) {
                        completeTask(task, error);
                        if (accepting.get()) {
                            partitionHealthy[partition].set(false);
                            failQueuedPartition(queueFor(partition), error);
                        }
                        return false;
                    }
                }
            }
        }

        partitionHealthy[partition].set(false);
        IllegalStateException terminal = new IllegalStateException(
                "scene persistence partition halted, partition=" + partition
                        + ", playerId=" + task.projection().playerId()
                        + ", revision=" + task.projection().revision(),
                lastError);
        completeTask(task, terminal);
        LoggerDef.DeadLetterLogger.error(
                "scene player projection permanently failed, partition={}, playerId={}, sceneId={}, revision={}, fogBytes={}",
                partition,
                task.projection().playerId(),
                task.projection().sceneId(),
                task.projection().revision(),
                task.projection().discoveredBlocks().toByteArray().length,
                terminal);
        failQueuedPartition(queueFor(partition), terminal);
        return false;
    }

    private boolean sleepBeforeRetry(int attempt) {
        long multiplier = 1L << Math.min(attempt, 16);
        long delayMillis = Math.min(MAX_RETRY_MILLIS, initialRetryMillis * multiplier);
        try {
            Thread.sleep(delayMillis);
            return true;
        } catch (InterruptedException error) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    private ArrayBlockingQueue<PersistTask> queueFor(int partition) {
        return queues.get(partition);
    }

    private void failQueuedPartition(ArrayBlockingQueue<PersistTask> queue, Throwable error) {
        PersistTask queued;
        while ((queued = queue.poll()) != null) {
            completeTask(queued, error);
        }
    }

    /** 先更新未完成计数，再唤醒 Future 等待者，保证 get() 返回后 pendingTasks 已可见为最新值。 */
    private void completeTask(PersistTask task, Throwable error) {
        outstandingTasks.decrementAndGet();
        if (error == null) {
            task.completion().complete(null);
        } else {
            task.completion().completeExceptionally(error);
        }
    }

    private record PersistTask(
            PlayerSceneProjection projection,
            CompletableFuture<Void> completion) {
    }
}
