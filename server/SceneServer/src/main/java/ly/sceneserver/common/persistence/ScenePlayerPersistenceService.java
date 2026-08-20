package ly.sceneserver.common.persistence;

import java.util.concurrent.CompletableFuture;

import ly.sceneserver.common.SceneRuntime;

/**
 * 玩家场景投影异步入库入口。
 *
 * <p>同一个 playerId 固定进入同一个 FIFO 分区。底层通用顺序队列负责重试、背压和死信；
 * PlayerSceneStore 负责把投影转换成 PlayerSceneEntry，业务层不接触 SQL。
 */
public final class ScenePlayerPersistenceService implements AutoCloseable {
    private static final int DEFAULT_PARTITIONS = 4;
    private static final int DEFAULT_QUEUE_CAPACITY = 5_000;
    private static final int DEFAULT_MAX_RETRIES = 20;
    private static final long DEFAULT_INITIAL_RETRY_MILLIS = 200L;

    private final OrderedPersistenceQueue<PlayerSceneProjection> queue;

    public ScenePlayerPersistenceService(PlayerSceneStore store) {
        this(
                store,
                Math.max(1, Integer.getInteger("slg.scene.persistence.partitions", DEFAULT_PARTITIONS)),
                Math.max(1, Integer.getInteger(
                        "slg.scene.persistence.queue-capacity", DEFAULT_QUEUE_CAPACITY)),
                Math.max(0, Integer.getInteger(
                        "slg.scene.persistence.max-retries", DEFAULT_MAX_RETRIES)),
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
        if (store == null) {
            throw new IllegalArgumentException("player scene store cannot be null");
        }
        this.queue = new OrderedPersistenceQueue<>(
                "scene-player-persist-",
                "scene player persistence",
                partitionCount,
                queueCapacity,
                maxRetries,
                initialRetryMillis,
                PlayerSceneProjection::playerId,
                PlayerSceneProjection::revision,
                projection -> "sceneId=" + projection.sceneId()
                        + ", playerId=" + projection.playerId(),
                store::upsert);
    }

    /** 提交不可变投影；Future 成功后才能清理对应内存脏状态。 */
    public CompletableFuture<Void> submit(PlayerSceneProjection projection) {
        return queue.submit(projection);
    }

    /**
     * 先从各 SceneShard Tick 取得玩家最新个人迷雾，再保存完整玩家场景实体。
     *
     * <p>调用方必须给 metadata 分配单调递增 revision；Future 成功前不能标记为已落库。
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
}
