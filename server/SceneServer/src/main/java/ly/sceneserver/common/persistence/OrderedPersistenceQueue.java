package ly.sceneserver.common.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToLongFunction;

import ly.LoggerDef;

/**
 * 按业务主键固定分区、在分区内严格 FIFO 执行的通用异步持久化队列。
 *
 * <p>玩家场景数据、地图对象、行军和集结都复用这一份可靠顺序写入实现。业务层只提交不可变
 * 快照；具体快照如何转换为 Entry 由各自 Store 负责，本队列不理解表结构，也不包含 SQL。
 *
 * <p>某条任务超过最大重试次数后会停止整个分区。这样后续 revision 不会越过失败版本继续
 * 写库；对应 Future 明确失败，并写入死信日志，不能把丢数据伪装成正常完成。
 */
final class OrderedPersistenceQueue<T> implements AutoCloseable {
    private static final long MAX_RETRY_MILLIS = 60_000L;

    private final List<ArrayBlockingQueue<PersistTask<T>>> queues;
    private final List<Thread> workers;
    private final AtomicBoolean accepting = new AtomicBoolean(true);
    private final AtomicInteger outstandingTasks = new AtomicInteger();
    private final AtomicBoolean[] partitionHealthy;
    private final int maxRetries;
    private final long initialRetryMillis;
    private final ToLongFunction<T> partitionKey;
    private final ToLongFunction<T> revision;
    private final Function<T, String> description;
    private final Consumer<T> persister;
    private final String logName;

    OrderedPersistenceQueue(
            String workerNamePrefix,
            String logName,
            int partitionCount,
            int queueCapacity,
            int maxRetries,
            long initialRetryMillis,
            ToLongFunction<T> partitionKey,
            ToLongFunction<T> revision,
            Function<T, String> description,
            Consumer<T> persister) {
        if (workerNamePrefix == null || workerNamePrefix.isBlank()
                || logName == null || logName.isBlank()
                || partitionCount <= 0 || queueCapacity <= 0 || maxRetries < 0
                || initialRetryMillis <= 0L || partitionKey == null || revision == null
                || description == null || persister == null) {
            throw new IllegalArgumentException("invalid ordered persistence queue parameters");
        }
        this.maxRetries = maxRetries;
        this.initialRetryMillis = initialRetryMillis;
        this.partitionKey = partitionKey;
        this.revision = revision;
        this.description = description;
        this.persister = persister;
        this.logName = logName;
        this.queues = new ArrayList<>(partitionCount);
        this.partitionHealthy = new AtomicBoolean[partitionCount];
        this.workers = new ArrayList<>(partitionCount);
        for (int i = 0; i < partitionCount; i++) {
            queues.add(new ArrayBlockingQueue<>(queueCapacity));
            partitionHealthy[i] = new AtomicBoolean(true);
            int partition = i;
            workers.add(Thread.ofVirtual()
                    .name(workerNamePrefix + partition)
                    .start(() -> runWorker(partition)));
        }
    }

    /** Future 成功表示 Store 已经接受该 revision，调用方此时才能清理内存脏标记。 */
    CompletableFuture<Void> submit(T value) {
        CompletableFuture<Void> completion = new CompletableFuture<>();
        if (value == null) {
            completion.completeExceptionally(new IllegalArgumentException("persistence value cannot be null"));
            return completion;
        }
        int partition = Math.floorMod(Long.hashCode(partitionKey.applyAsLong(value)), queues.size());
        if (!accepting.get()) {
            completion.completeExceptionally(new IllegalStateException(logName + " is shutting down"));
            return completion;
        }
        if (!partitionHealthy[partition].get()) {
            completion.completeExceptionally(new IllegalStateException(
                    logName + " partition is halted: " + partition));
            return completion;
        }

        PersistTask<T> task = new PersistTask<>(value, completion);
        outstandingTasks.incrementAndGet();
        if (!queues.get(partition).offer(task)) {
            completeTask(task, new IllegalStateException(logName + " queue is full: " + partition));
            LoggerDef.DbLogger.error(
                    "{} queue full, partition={}, revision={}, data={}",
                    logName, partition, revision.applyAsLong(value), description.apply(value));
        }
        return completion;
    }

    int pendingTasks() {
        return outstandingTasks.get();
    }

    boolean isHealthy() {
        for (AtomicBoolean healthy : partitionHealthy) {
            if (!healthy.get()) {
                return false;
            }
        }
        return true;
    }

    /** 停服时尽量排空队列；超时后的未执行任务会显式失败，不会静默丢弃。 */
    void shutdown(long timeoutMillis) {
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
        IllegalStateException error = new IllegalStateException(logName + " stopped before task completed");
        for (ArrayBlockingQueue<PersistTask<T>> queue : queues) {
            PersistTask<T> task;
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
        ArrayBlockingQueue<PersistTask<T>> queue = queues.get(partition);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                PersistTask<T> task = queue.poll(1L, TimeUnit.SECONDS);
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

    private boolean persistInOrder(int partition, PersistTask<T> task) {
        Throwable lastError = null;
        for (int attempt = 0; attempt <= maxRetries; attempt++) {
            try {
                persister.accept(task.value());
                completeTask(task, null);
                return true;
            } catch (Throwable error) {
                lastError = error;
                if (attempt < maxRetries && !sleepBeforeRetry(attempt)) {
                    completeTask(task, error);
                    if (accepting.get()) {
                        partitionHealthy[partition].set(false);
                        failQueuedPartition(queues.get(partition), error);
                    }
                    return false;
                }
            }
        }

        partitionHealthy[partition].set(false);
        T value = task.value();
        IllegalStateException terminal = new IllegalStateException(
                logName + " partition halted, partition=" + partition
                        + ", revision=" + revision.applyAsLong(value)
                        + ", data=" + description.apply(value),
                lastError);
        completeTask(task, terminal);
        LoggerDef.DeadLetterLogger.error(
                "{} permanently failed, partition={}, revision={}, data={}",
                logName, partition, revision.applyAsLong(value), description.apply(value), terminal);
        failQueuedPartition(queues.get(partition), terminal);
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

    private void failQueuedPartition(ArrayBlockingQueue<PersistTask<T>> queue, Throwable error) {
        PersistTask<T> queued;
        while ((queued = queue.poll()) != null) {
            completeTask(queued, error);
        }
    }

    private void completeTask(PersistTask<T> task, Throwable error) {
        outstandingTasks.decrementAndGet();
        if (error == null) {
            task.completion().complete(null);
        } else {
            task.completion().completeExceptionally(error);
        }
    }

    private record PersistTask<T>(T value, CompletableFuture<Void> completion) {
    }
}
