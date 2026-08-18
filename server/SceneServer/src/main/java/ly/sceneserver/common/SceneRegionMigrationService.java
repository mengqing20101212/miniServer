package ly.sceneserver.common;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 热点 Region 数据搬运使用的单线程执行器。
 *
 * <p>只有快照编排、完整性校验和所有权切换在该线程执行；源数据导出和目标数据安装仍然回投
 * 对应 SceneShard Tick。因此无论迁移任务有多少，任何 SceneObject 都不会被迁移线程直接修改。
 * 单线程还天然串行化了大块内存复制，避免多个热点迁移同时抢占 CPU、内存带宽和 GC 预算。
 */
final class SceneRegionMigrationService implements AutoCloseable {
    private static final int DEFAULT_QUEUE_CAPACITY = 128;
    private static final long CLOSE_WAIT_SECONDS = 10L;

    private final ThreadPoolExecutor executor;
    private final AtomicLong submittedTasks = new AtomicLong();
    private final AtomicLong finishedTasks = new AtomicLong();
    private final AtomicLong succeededTasks = new AtomicLong();
    private final AtomicLong failedTasks = new AtomicLong();
    private final AtomicLong rejectedTasks = new AtomicLong();
    private final AtomicLong totalTaskNanos = new AtomicLong();
    private final AtomicLong intervalMaxTaskNanos = new AtomicLong();

    SceneRegionMigrationService() {
        int queueCapacity = Math.max(1, Integer.getInteger(
                "slg.scene.region-migration.queue-capacity", DEFAULT_QUEUE_CAPACITY));
        this.executor = new ThreadPoolExecutor(
                1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueCapacity),
                Thread.ofPlatform().daemon(true).name("SceneRegion-Migration-", 0).factory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    <T> CompletableFuture<T> submit(Callable<T> task) {
        CompletableFuture<T> result = new CompletableFuture<>();
        submittedTasks.incrementAndGet();
        try {
            executor.execute(() -> runTask(task, result));
        } catch (RejectedExecutionException error) {
            rejectedTasks.incrementAndGet();
            result.completeExceptionally(error);
        }
        return result;
    }

    SceneRegionMigrationLoadSnapshot loadSnapshotAndResetPeak() {
        return new SceneRegionMigrationLoadSnapshot(
                executor.getPoolSize(),
                executor.getActiveCount(),
                executor.getQueue().size(),
                submittedTasks.get(),
                finishedTasks.get(),
                succeededTasks.get(),
                failedTasks.get(),
                rejectedTasks.get(),
                totalTaskNanos.get(),
                intervalMaxTaskNanos.getAndSet(0L),
                System.nanoTime());
    }

    @Override
    public void close() {
        // 先优雅停止，让正在等待源/目标 Tick 的迁移完成；此时 SceneRuntime 尚未关闭 Tick 池。
        executor.shutdown();
        try {
            if (!executor.awaitTermination(CLOSE_WAIT_SECONDS, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException error) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private <T> void runTask(Callable<T> task, CompletableFuture<T> result) {
        long startedNanos = System.nanoTime();
        try {
            result.complete(task.call());
            succeededTasks.incrementAndGet();
        } catch (Throwable error) {
            failedTasks.incrementAndGet();
            result.completeExceptionally(error);
        } finally {
            long durationNanos = Math.max(0L, System.nanoTime() - startedNanos);
            totalTaskNanos.addAndGet(durationNanos);
            intervalMaxTaskNanos.accumulateAndGet(durationNanos, Math::max);
            finishedTasks.incrementAndGet();
        }
    }
}
