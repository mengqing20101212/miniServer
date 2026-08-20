package ly.sceneserver.common.persistence;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import ly.sceneserver.common.SceneObject;

/**
 * 普通动态地图对象、行军和集结的统一异步入库入口。
 *
 * <p>同一个聚合 ID 固定进入同一 FIFO 分区，失败原地重试；Store 再通过实体 revision UPSERT
 * 拒绝旧快照。Future 成功才表示对应 Entry 已被数据库接受，业务层才能清除脏标记或回收对象。
 */
public final class SceneAggregatePersistenceService implements AutoCloseable {
    private static final int DEFAULT_PARTITIONS = 4;
    private static final int DEFAULT_QUEUE_CAPACITY = 10_000;
    private static final int DEFAULT_MAX_RETRIES = 20;
    private static final long DEFAULT_INITIAL_RETRY_MILLIS = 200L;

    private final OrderedPersistenceQueue<SceneAggregateProjection> queue;

    public SceneAggregatePersistenceService(SceneAggregateStore store) {
        this(
                store,
                Math.max(1, Integer.getInteger(
                        "slg.scene.aggregate-persistence.partitions", DEFAULT_PARTITIONS)),
                Math.max(1, Integer.getInteger(
                        "slg.scene.aggregate-persistence.queue-capacity", DEFAULT_QUEUE_CAPACITY)),
                Math.max(0, Integer.getInteger(
                        "slg.scene.aggregate-persistence.max-retries", DEFAULT_MAX_RETRIES)),
                Math.max(1L, Long.getLong(
                        "slg.scene.aggregate-persistence.initial-retry-millis",
                        DEFAULT_INITIAL_RETRY_MILLIS)));
    }

    SceneAggregatePersistenceService(
            SceneAggregateStore store,
            int partitionCount,
            int queueCapacity,
            int maxRetries,
            long initialRetryMillis) {
        if (store == null) {
            throw new IllegalArgumentException("scene aggregate store cannot be null");
        }
        this.queue = new OrderedPersistenceQueue<>(
                "scene-aggregate-persist-",
                "scene aggregate persistence",
                partitionCount,
                queueCapacity,
                maxRetries,
                initialRetryMillis,
                SceneAggregatePersistenceService::partitionKey,
                SceneAggregateProjection::revision,
                projection -> "type=" + projection.getClass().getSimpleName()
                        + ", sceneId=" + projection.sceneId()
                        + ", aggregateId=" + projection.aggregateId(),
                projection -> persist(store, projection));
    }

    /** 提交已经在 Tick 线程冻结好的不可变投影。 */
    public CompletableFuture<Void> submit(SceneAggregateProjection projection) {
        return queue.submit(projection);
    }

    /**
     * 在 SceneShard Tick 线程中冻结对象并提交；异步线程只接触投影，不读取可变对象。
     *
     * <p>删除也使用实体软删除字段和新 revision UPSERT。只有 Future 成功后才能从内存彻底回收
     * 该对象，否则数据库恢复时仍可能重新加载旧对象。
     */
    public CompletableFuture<Void> snapshotAndSubmit(
            String sceneId,
            SceneObject object,
            int dataVersion,
            boolean deleted) {
        return submit(SceneAggregateProjectionFactory.snapshot(
                sceneId, object, dataVersion, deleted, LocalDateTime.now()));
    }

    public int pendingTasks() {
        return queue.pendingTasks();
    }

    public boolean isHealthy() {
        return queue.isHealthy();
    }

    public void shutdown(long timeoutMillis) {
        queue.shutdown(timeoutMillis);
    }

    @Override
    public void close() {
        queue.close();
    }

    private static void persist(SceneAggregateStore store, SceneAggregateProjection projection) {
        switch (projection) {
            case SceneObjectProjection object -> store.upsert(object);
            case SceneMarchProjection march -> store.upsert(march);
            case SceneRallyProjection rally -> store.upsert(rally);
        }
    }

    /** sceneId 参与哈希，避免不同分区服碰巧使用相同对象 ID 时集中到同一队列。 */
    private static long partitionKey(SceneAggregateProjection projection) {
        long sceneHash = Integer.toUnsignedLong(projection.sceneId().hashCode());
        return (sceneHash << 32) ^ projection.aggregateId();
    }
}
