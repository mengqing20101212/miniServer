package ly.sceneserver.common;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

import ly.LoggerDef;

/**
 * SceneServer 进程内的场景运行时。
 *
 * <p>一个运行时可以承载多个逻辑场景，每个逻辑场景拥有多个 SceneShard。静态地图由所有
 * SceneShard 共享，动态对象先按坐标定位 Region，再由 RegionDirectory 路由到唯一分片。
 * 当前只支持同一 JVM 内的热点 Region 迁移，第一阶段不引入跨进程地图迁移。
 */
public final class SceneRuntime implements AutoCloseable {
    private static final long DEFAULT_LOAD_LOG_SECONDS = 60L;
    private static final int DEFAULT_SLOW_TICK_MILLIS = 200;
    private static final int DEFAULT_QUEUE_WARN_THRESHOLD = 1_000;

    private final Map<String, SceneInstance> scenes = new ConcurrentHashMap<>();
    private final ScheduledExecutorService tickExecutor;
    private final ScenePathService pathService;
    /** 所有逻辑场景共用一个迁移线程，避免多个大 Region 同时争抢内存带宽。 */
    private final SceneRegionMigrationService regionMigrationService;
    private final SceneLoadLogger loadLogger;
    private final int slowTickMillis;
    private volatile boolean started;

    public SceneRuntime() {
        this(
                loadLogIntervalMillis(),
                positiveIntProperty("slg.scene.slow-tick-millis", DEFAULT_SLOW_TICK_MILLIS),
                positiveIntProperty("slg.scene.load.queue-warn", DEFAULT_QUEUE_WARN_THRESHOLD));
    }

    SceneRuntime(long loadLogIntervalMillis, int slowTickMillis, int queueWarnThreshold) {
        if (loadLogIntervalMillis < 0L || slowTickMillis <= 0 || queueWarnThreshold <= 0) {
            throw new IllegalArgumentException("invalid SceneRuntime load monitor parameters");
        }
        ThreadFactory threadFactory = Thread.ofPlatform()
                .daemon(true)
                .name("SceneShard-Tick-", 0)
                .factory();
        this.tickExecutor = Executors.newScheduledThreadPool(
                Math.max(1, Runtime.getRuntime().availableProcessors()), threadFactory);
        this.pathService = new ScenePathService();
        this.regionMigrationService = new SceneRegionMigrationService();
        this.slowTickMillis = slowTickMillis;
        this.loadLogger = new SceneLoadLogger(
                this,
                pathService,
                regionMigrationService,
                loadLogIntervalMillis,
                slowTickMillis,
                queueWarnThreshold);
    }

    public void addScene(SceneConfig config) {
        addScene(config, null);
    }

    public void addScene(SceneConfig config, SceneTickListener tickListener) {
        if (started) {
            throw new IllegalStateException("cannot add scene after runtime started");
        }
        SceneInstance instance = new SceneInstance(config, tickListener);
        if (scenes.putIfAbsent(config.sceneId(), instance) != null) {
            throw new IllegalArgumentException("scene already exists: " + config.sceneId());
        }
    }

    public SceneInstance scene(String sceneId) {
        SceneInstance scene = scenes.get(sceneId);
        if (scene == null) {
            throw new IllegalArgumentException("scene not found: " + sceneId);
        }
        return scene;
    }

    /** 返回稳定排序的已注册场景 ID，供启动恢复按固定顺序加载。 */
    public List<String> registeredSceneIds() {
        return scenes.keySet().stream().sorted().toList();
    }

    public void start() {
        if (started) {
            return;
        }
        // 启动顺序不能交换：
        // 1. SceneRecoveryService/策划地图加载器已经写完 terrain、flags 等静态数组；
        // 2. freeze 后禁止运行期继续修改，保证 Region 图和格子 A* 看到同一版本；
        // 3. 在启动线程预建 Region/Portal 连通图，避免首个玩家请求承担百万格扫描；
        // 4. 最后才把 started 置为 true，并启动 SceneShard Tick 和负载日志。
        for (SceneInstance scene : scenes.values()) {
            scene.staticMap.freeze();
            pathService.prepareMap(scene.staticMap, scene.config.regionSize());
        }
        started = true;
        for (SceneInstance scene : scenes.values()) {
            scene.start();
        }
        loadLogger.start();
        LoggerDef.SystemLogger.info("SceneRuntime started, sceneCount={}", scenes.size());
    }

    public boolean isStarted() {
        return started;
    }

    @Override
    public void close() {
        loadLogger.close();
        // 迁移线程可能正在等待源/目标 Tick 完成导出或安装，必须在关闭 Tick 池之前优雅停止。
        regionMigrationService.close();
        if (!started) {
            tickExecutor.shutdownNow();
            pathService.close();
            return;
        }
        started = false;
        tickExecutor.shutdownNow();
        pathService.close();
        LoggerDef.SystemLogger.info("SceneRuntime stopped");
    }

    /** 立即输出 SceneShard、寻路线程池和实际平台线程负载，供 GM/线上诊断调用。 */
    public void logLoadNow() {
        loadLogger.logNow();
    }

    /** 仅由负载监控调用；快照内部不暴露 SceneShard 可变集合。 */
    List<SceneShardLoadSnapshot> collectShardLoadSnapshots() {
        ArrayList<SceneShardLoadSnapshot> snapshots = new ArrayList<>();
        for (SceneInstance scene : scenes.values()) {
            for (SceneShard shard : scene.shards) {
                snapshots.add(shard.loadSnapshotAndResetPeaks());
            }
        }
        snapshots.sort(Comparator
                .comparing(SceneShardLoadSnapshot::sceneId)
                .thenComparingInt(SceneShardLoadSnapshot::shardIndex));
        return snapshots;
    }

    /** 一个逻辑场景及其 SceneShard 集合。 */
    public final class SceneInstance {
        private final SceneConfig config;
        private final SceneStaticMap staticMap;
        private final SceneRegionDirectory regionDirectory;
        private final SceneShard[] shards;

        private SceneInstance(SceneConfig config, SceneTickListener tickListener) {
            this.config = config;
            this.staticMap = new SceneStaticMap(config.width(), config.height());
            this.regionDirectory = new SceneRegionDirectory(config);
            this.shards = new SceneShard[config.shardCount()];
            for (int i = 0; i < shards.length; i++) {
                int minX = config.width() * i / shards.length;
                int maxX = config.width() * (i + 1) / shards.length;
                shards[i] = new SceneShard(
                        config,
                        staticMap,
                        regionDirectory,
                        i,
                        minX,
                        maxX,
                        tickListener,
                        slowTickMillis);
            }
        }

        private void start() {
            for (SceneShard shard : shards) {
                tickExecutor.scheduleAtFixedRate(
                        shard::tick,
                        config.tickMillis(),
                        config.tickMillis(),
                        TimeUnit.MILLISECONDS);
            }
        }

        public SceneConfig config() {
            return config;
        }

        public SceneStaticMap staticMap() {
            return staticMap;
        }

        public SceneShard shard(int shardIndex) {
            return shards[shardIndex];
        }

        public int shardCount() {
            return shards.length;
        }

        public SceneShard route(int x, int y) {
            if (x < 0 || x >= config.width() || y < 0 || y >= config.height()) {
                throw new IndexOutOfBoundsException("scene point out of bounds: " + x + "," + y);
            }
            int regionIndex = regionDirectory.regionIndex(x, y);
            int index = regionDirectory.ownerShardIndex(regionIndex);
            return shards[index];
        }

        /**
         * 按坐标向当前 Region 所有者提交命令。
         *
         * <p>业务代码应使用该入口，而不是先调用 route() 再直接向 SceneShard 提交。迁移期间
         * 本方法会暂存新命令，并在切换成功或回滚后按原顺序释放到正确的所有者。
         */
        public CompletableFuture<Void> submit(int x, int y, Consumer<SceneShard> action) {
            if (x < 0 || x >= config.width() || y < 0 || y >= config.height()) {
                return CompletableFuture.failedFuture(
                        new IndexOutOfBoundsException("scene point out of bounds: " + x + "," + y));
            }
            return regionDirectory.dispatch(regionDirectory.regionIndex(x, y), shards, action);
        }

        public int regionCount() {
            return regionDirectory.regionCount();
        }

        public int regionOwnerShardIndex(int regionIndex) {
            return regionDirectory.ownerShardIndex(regionIndex);
        }

        public boolean isRegionMigrating(int regionIndex) {
            return regionDirectory.isMigrating(regionIndex);
        }

        /**
         * 把热点 Region 迁移到负载较低的目标 SceneShard。
         *
         * <p>该 API 只负责执行已确定的迁移决策。后续自动均衡器应根据连续多个采样周期的
         * Region 命令数、对象数和 Tick 耗时选择候选块，并设置冷却时间，不能看到一次尖峰就抖动。
         */
        public CompletableFuture<SceneRegionMigrationResult> migrateRegionAsync(
                int regionIndex,
                int targetShardIndex) {
            if (!started) {
                return CompletableFuture.failedFuture(
                        new IllegalStateException("SceneRuntime must be started before region migration"));
            }
            if (regionIndex < 0 || regionIndex >= regionDirectory.regionCount()) {
                return CompletableFuture.failedFuture(
                        new IndexOutOfBoundsException("region index out of bounds: " + regionIndex));
            }
            if (targetShardIndex < 0 || targetShardIndex >= shards.length) {
                return CompletableFuture.failedFuture(
                        new IndexOutOfBoundsException("shard index out of bounds: " + targetShardIndex));
            }
            return regionMigrationService.submit(() -> migrateRegion(regionIndex, targetShardIndex));
        }

        /**
         * 移动玩家视野并更新 AOI 块注册。
         *
         * <p>视野可能横跨多个 SceneShard，因此请求会投递到每个分片，再合并为一个客户端快照。
         * radiusBlocks 的单位是 Region/AOI 块，不是地图格子。
         */
        public CompletableFuture<SceneViewSnapshot> updateViewAsync(SceneViewRequest request) {
            if (!inBounds(request.center())) {
                return CompletableFuture.failedFuture(
                        new IndexOutOfBoundsException("scene view center out of bounds"));
            }
            int maxRadius = Math.max(blockColumns(), blockRows());
            if (request.radiusBlocks() > maxRadius) {
                return CompletableFuture.failedFuture(
                        new IllegalArgumentException("scene view radius is too large"));
            }
            return withStableRegionOwnership(() -> {
                BitSet visibleBlocks = visibleBlocks(request.center(), request.radiusBlocks());
                List<CompletableFuture<SceneViewSnapshot>> futures = new ArrayList<>(shards.length);
                for (SceneShard shard : shards) {
                    futures.add(shard.query(current -> current.updateViewer(request, visibleBlocks)));
                }
                return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
                        .thenApply(ignored -> mergeViewSnapshots(request, futures));
            });
        }

        /** 玩家离开时从所有相交的 AOI 块取消注册；历史探索迷雾继续按 playerId 保留。 */
        public CompletableFuture<Void> removeViewerAsync(long playerId) {
            return withStableRegionOwnership(() -> {
                CompletableFuture<?>[] futures = new CompletableFuture<?>[shards.length];
                for (int i = 0; i < shards.length; i++) {
                    futures[i] = shards[i].submit(shard -> shard.removeViewer(playerId));
                }
                return CompletableFuture.allOf(futures);
            });
        }

        /** 从玩家持久化数据恢复全场景探索块，各 SceneShard 只保留与自己相交的部分。 */
        public CompletableFuture<Void> restoreDiscoveredBlocksAsync(long playerId, BitSet discoveredBlocks) {
            BitSet immutableInput = (BitSet) discoveredBlocks.clone();
            return withStableRegionOwnership(() -> {
                CompletableFuture<?>[] futures = new CompletableFuture<?>[shards.length];
                for (int i = 0; i < shards.length; i++) {
                    futures[i] = shards[i].submit(shard ->
                            shard.restoreDiscoveredBlocks(playerId, immutableInput));
                }
                return CompletableFuture.allOf(futures);
            });
        }

        /** 聚合玩家的探索块快照，用于异步落库；每个玩家得到完全独立的 BitSet。 */
        public CompletableFuture<BitSet> discoveredBlocksSnapshotAsync(long playerId) {
            return withStableRegionOwnership(() -> {
                List<CompletableFuture<BitSet>> futures = new ArrayList<>(shards.length);
                for (SceneShard shard : shards) {
                    futures.add(shard.query(current -> current.discoveredBlocksSnapshot(playerId)));
                }
                return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
                        .thenApply(ignored -> {
                            BitSet result = new BitSet();
                            for (CompletableFuture<BitSet> future : futures) {
                                result.or(future.join());
                            }
                            return result;
                        });
            });
        }

        /** 迷雾成功持久化且玩家已经离线后，显式释放该玩家的探索状态。 */
        public CompletableFuture<Void> evictDiscoveredBlocksAsync(long playerId) {
            return withStableRegionOwnership(() -> {
                CompletableFuture<?>[] futures = new CompletableFuture<?>[shards.length];
                for (int i = 0; i < shards.length; i++) {
                    futures[i] = shards[i].submit(shard -> shard.evictDiscoveredBlocks(playerId));
                }
                return CompletableFuture.allOf(futures);
            });
        }

        /** 收集当前可见块和历史已探索块，供异步寻路线程只读使用。 */
        public CompletableFuture<SceneVisibilitySnapshot> visibilitySnapshotAsync(long playerId) {
            return withStableRegionOwnership(() -> {
                List<CompletableFuture<SceneVisibilitySnapshot>> futures = new ArrayList<>(shards.length);
                for (SceneShard shard : shards) {
                    futures.add(shard.query(current -> current.visibilitySnapshot(playerId)));
                }
                return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
                        .thenApply(ignored -> {
                            BitSet visible = new BitSet();
                            BitSet discovered = new BitSet();
                            for (CompletableFuture<SceneVisibilitySnapshot> future : futures) {
                                SceneVisibilitySnapshot snapshot = future.join();
                                visible.or(snapshot.visibleBlocks());
                                discovered.or(snapshot.discoveredBlocks());
                            }
                            return new SceneVisibilitySnapshot(
                                    config.regionSize(), blockColumns(), visible, discovered);
                        });
            });
        }

        /**
         * 异步执行 A*，并把完成结果投递回起点所属 SceneShard 的下一个 Tick。
         *
         * <p>返回 Future 的监听器会在 Tick 线程中被触发；RPC Handler 可在该监听器中恢复
         * callId 后发送响应，寻路线程绝不直接修改场景对象或响应网络连接。
         */
        public CompletableFuture<ScenePathResult> findPathAsync(ScenePathRequest request) {
            CompletableFuture<SceneVisibilitySnapshot> visibilityFuture;
            if (request.fogPolicy() == SceneFogPolicy.IGNORE) {
                visibilityFuture = CompletableFuture.completedFuture(new SceneVisibilitySnapshot(
                        config.regionSize(), blockColumns(), new BitSet(), new BitSet()));
            } else {
                visibilityFuture = visibilitySnapshotAsync(request.playerId());
            }

            CompletableFuture<ScenePathResult> workerFuture = visibilityFuture.thenCompose(visibility ->
                    pathService.findPathAsync(staticMap, request, visibility));
            CompletableFuture<ScenePathResult> returnedOnTick = new CompletableFuture<>();
            workerFuture.whenComplete((pathResult, error) -> {
                try {
                    submit(request.start().x(), request.start().y(), shard -> {
                        if (error == null) {
                            // inbox 在 tickNumber 自增前 drain，因此当前正在执行的是下一个 tick。
                            returnedOnTick.complete(pathResult.completedOn(shard.tickNumber() + 1));
                        } else {
                            returnedOnTick.completeExceptionally(error);
                        }
                    }).exceptionally(dispatchError -> {
                        returnedOnTick.completeExceptionally(dispatchError);
                        return null;
                    });
                } catch (Throwable dispatchError) {
                    returnedOnTick.completeExceptionally(dispatchError);
                }
            });
            return returnedOnTick;
        }

        /** 启动前加载假数据或地图快照时使用，运行中业务必须通过 submit。 */
        public void seedObject(SceneObject object) {
            if (started) {
                throw new IllegalStateException("cannot seed scene object after runtime started");
            }
            route(object.x(), object.y()).addObject(object);
        }

        /** 启动前恢复玩家永久探索记录；运行后必须使用异步投递接口。 */
        public void restoreDiscoveredBlocksBeforeStart(long playerId, BitSet discoveredBlocks) {
            if (started) {
                throw new IllegalStateException("cannot restore fog directly after runtime started");
            }
            for (SceneShard shard : shards) {
                shard.restoreDiscoveredBlocks(playerId, discoveredBlocks);
            }
        }

        /** 查找对象所属分片，结果在各分片逻辑线程完成后返回。 */
        public CompletableFuture<SceneShard> locateObject(long objectId) {
            return withStableRegionOwnership(() -> {
                CompletableFuture<SceneShard> result = new CompletableFuture<>();
                AtomicInteger pending = new AtomicInteger(shards.length);
                for (SceneShard shard : shards) {
                    java.util.concurrent.atomic.AtomicBoolean found = new java.util.concurrent.atomic.AtomicBoolean();
                    shard.submit(current -> found.set(current.object(objectId) != null))
                            .whenComplete((ignored, error) -> {
                                if (error != null) {
                                    result.completeExceptionally(error);
                                } else if (found.get()) {
                                    result.complete(shard);
                                } else if (pending.decrementAndGet() == 0) {
                                    result.complete(null);
                                }
                            });
                }
                return result;
            });
        }

        /** 当前场景中动态对象数量，主要用于启动指标和测试；精确统计由后续 tick 指标完善。 */
        public int totalObjectCount() {
            int count = 0;
            for (SceneShard shard : shards) {
                count += shard.objectCount();
            }
            return count;
        }

        /**
         * 在各个 SceneShard 的逻辑队列中读取动态对象数量，避免 RPC 指标线程直接读取 HashMap。
         *
         * <p>启动前没有逻辑线程，直接读取用于启动校验；运行中则所有读取都经过对应分片队列。
         */
        public CompletableFuture<Integer> totalObjectCountAsync() {
            if (!started) {
                return CompletableFuture.completedFuture(totalObjectCount());
            }
            return withStableRegionOwnership(() -> {
                CompletableFuture<Integer> result = new CompletableFuture<>();
                AtomicInteger pending = new AtomicInteger(shards.length);
                AtomicInteger total = new AtomicInteger();
                AtomicReference<Throwable> failure = new AtomicReference<>();
                for (SceneShard shard : shards) {
                    shard.submit(current -> total.addAndGet(current.objectCount()))
                            .whenComplete((ignored, error) -> {
                                if (error != null) {
                                    failure.compareAndSet(null, error);
                                }
                                if (pending.decrementAndGet() == 0) {
                                    Throwable cause = failure.get();
                                    if (cause == null) {
                                        result.complete(total.get());
                                    } else {
                                        result.completeExceptionally(cause);
                                    }
                                }
                            });
                }
                return result;
            });
        }

        public long maxTickNumber() {
            long max = 0;
            for (SceneShard shard : shards) {
                max = Math.max(max, shard.tickNumber());
            }
            return max;
        }

        private boolean inBounds(ScenePoint point) {
            return point.x() >= 0
                    && point.x() < config.width()
                    && point.y() >= 0
                    && point.y() < config.height();
        }

        private int blockColumns() {
            return (config.width() + config.regionSize() - 1) / config.regionSize();
        }

        private int blockRows() {
            return (config.height() + config.regionSize() - 1) / config.regionSize();
        }

        private BitSet visibleBlocks(ScenePoint center, int radiusBlocks) {
            int centerBlockX = center.x() / config.regionSize();
            int centerBlockY = center.y() / config.regionSize();
            int minBlockX = Math.max(0, centerBlockX - radiusBlocks);
            int maxBlockX = Math.min(blockColumns() - 1, centerBlockX + radiusBlocks);
            int minBlockY = Math.max(0, centerBlockY - radiusBlocks);
            int maxBlockY = Math.min(blockRows() - 1, centerBlockY + radiusBlocks);
            BitSet result = new BitSet(blockColumns() * blockRows());
            for (int blockY = minBlockY; blockY <= maxBlockY; blockY++) {
                int rowStart = blockY * blockColumns();
                result.set(rowStart + minBlockX, rowStart + maxBlockX + 1);
            }
            return result;
        }

        private SceneViewSnapshot mergeViewSnapshots(
                SceneViewRequest request,
                List<CompletableFuture<SceneViewSnapshot>> futures) {
            ArrayList<SceneObjectSnapshot> objects = new ArrayList<>();
            Map<Integer, SceneBlockSnapshot> blocks = new HashMap<>();
            TreeSet<Integer> discovered = new TreeSet<>();
            long tickNumber = 0L;
            for (CompletableFuture<SceneViewSnapshot> future : futures) {
                SceneViewSnapshot snapshot = future.join();
                tickNumber = Math.max(tickNumber, snapshot.tickNumber());
                objects.addAll(snapshot.objects());
                discovered.addAll(snapshot.discoveredBlockIndices());
                for (SceneBlockSnapshot block : snapshot.blocks()) {
                    blocks.merge(block.blockIndex(), block, (left, right) -> new SceneBlockSnapshot(
                            left.blockIndex(),
                            left.blockX(),
                            left.blockY(),
                            left.visible() || right.visible(),
                            left.discovered() || right.discovered(),
                            left.objectCount() + right.objectCount(),
                            left.dataTagMask() | right.dataTagMask()));
                }
            }
            objects.sort(java.util.Comparator.comparingLong(SceneObjectSnapshot::objectId));
            ArrayList<SceneBlockSnapshot> sortedBlocks = new ArrayList<>(blocks.values());
            sortedBlocks.sort(java.util.Comparator.comparingInt(SceneBlockSnapshot::blockIndex));
            return new SceneViewSnapshot(
                    request.playerId(),
                    new ScenePoint(
                            request.center().x() / config.regionSize(),
                            request.center().y() / config.regionSize()),
                    request.viewLevel(),
                    tickNumber,
                    objects,
                    sortedBlocks,
                    new ArrayList<>(discovered));
        }

        /** 由唯一迁移线程调用；导出和安装本身分别在源、目标 SceneShard Tick 中执行。 */
        private SceneRegionMigrationResult migrateRegion(
                int regionIndex,
                int targetShardIndex) throws InterruptedException {
            long startedNanos = System.nanoTime();
            SceneRegionMigrationTicket ticket = regionDirectory.beginMigration(
                    regionIndex, targetShardIndex);
            SceneRegionTransfer transfer = null;
            try {
                SceneShard source = shards[ticket.sourceShardIndex()];
                SceneShard target = shards[ticket.targetShardIndex()];
                transfer = source.query(shard -> shard.exportRegion(ticket)).join();

                // 迁移线程只校验不可变的容器元数据，不触碰 SceneObject.state。
                validateTransfer(transfer);
                SceneRegionTransfer installing = transfer;
                AtomicReference<SceneRegionDirectory.Cutover> cutoverRef = new AtomicReference<>();
                try {
                    target.submit(shard -> {
                        shard.installRegion(installing);
                        // 安装与 owner 切换必须属于同一个目标 Tick 命令。这样该命令返回后，
                        // TickListener 看到新对象时 owns() 一定已经指向目标 Shard。
                        cutoverRef.set(regionDirectory.commitAndFlush(ticket, shards));
                    }).join();
                } catch (Throwable installError) {
                    // 目标安装采用“先全量校验、后修改集合”，失败时数据包仍可完整装回源端。
                    source.submit(shard -> shard.installRegion(installing)).join();
                    SceneRegionDirectory.Cutover rollback =
                            regionDirectory.abortAndFlush(ticket, shards);
                    rollback.releaseAggregateWaiters();
                    rollback.barrier().join();
                    throw installError;
                }

                SceneRegionDirectory.Cutover cutover = cutoverRef.get();
                if (cutover == null) {
                    throw new IllegalStateException("Region cutover barrier was not created");
                }
                // 在迁移线程而非目标 Tick 中恢复聚合请求；barrier 已经排在所有暂存命令之后。
                cutover.releaseAggregateWaiters();
                cutover.barrier().join();
                long durationNanos = Math.max(0L, System.nanoTime() - startedNanos);
                LoggerDef.SystemLogger.info(
                        "Scene Region migration completed, sceneId={}, region={}, sourceShard={}, targetShard={}, version={}, objects={}, playerStates={}, costMicros={}",
                        config.sceneId(), ticket.regionIndex(), ticket.sourceShardIndex(),
                        ticket.targetShardIndex(), ticket.version(), transfer.objects().size(),
                        transfer.players().size(), durationNanos / 1_000L);
                return new SceneRegionMigrationResult(
                        config.sceneId(),
                        ticket.regionIndex(),
                        ticket.sourceShardIndex(),
                        ticket.targetShardIndex(),
                        ticket.version(),
                        transfer.objects().size(),
                        transfer.players().size(),
                        durationNanos);
            } catch (Throwable error) {
                if (regionDirectory.matches(ticket)) {
                    // exportRegion 在修改集合前完成全部校验；若尚未生成 transfer，源状态仍然完整。
                    if (transfer != null) {
                        SceneRegionTransfer restoring = transfer;
                        shards[ticket.sourceShardIndex()]
                                .submit(shard -> shard.installRegion(restoring))
                                .join();
                    }
                    SceneRegionDirectory.Cutover rollback =
                            regionDirectory.abortAndFlush(ticket, shards);
                    rollback.releaseAggregateWaiters();
                    rollback.barrier().join();
                }
                LoggerDef.SystemLogger.error(
                        "Scene Region migration failed, sceneId={}, region={}, sourceShard={}, targetShard={}, version={}",
                        config.sceneId(), ticket.regionIndex(), ticket.sourceShardIndex(),
                        ticket.targetShardIndex(), ticket.version(), error);
                if (error instanceof RuntimeException runtimeError) {
                    throw runtimeError;
                }
                throw new IllegalStateException("region migration failed", error);
            }
        }

        private void validateTransfer(SceneRegionTransfer transfer) {
            if (!regionDirectory.matches(transfer.ticket())) {
                throw new IllegalStateException("migration transfer has a stale ticket");
            }
            java.util.HashSet<Long> objectIds = new java.util.HashSet<>();
            for (SceneObject object : transfer.objects()) {
                if (!objectIds.add(object.objectId())
                        || regionDirectory.regionIndex(object.x(), object.y())
                        != transfer.ticket().regionIndex()) {
                    throw new IllegalStateException(
                            "invalid object in Region transfer: " + object.objectId());
                }
            }
            java.util.HashSet<Long> playerIds = new java.util.HashSet<>();
            for (SceneRegionPlayerTransfer player : transfer.players()) {
                if (!playerIds.add(player.playerId())) {
                    throw new IllegalStateException(
                            "duplicate player in Region transfer: " + player.playerId());
                }
            }
        }

        /**
         * 让跨 Shard 聚合操作避开迁移切换窗口。
         *
         * <p>租约不是线程锁，可以在调用线程取得、在任意 CompletableFuture 回调线程释放。
         */
        private <T> CompletableFuture<T> withStableRegionOwnership(
                Supplier<CompletableFuture<T>> operation) {
            return regionDirectory.acquireAggregateLease().thenCompose(lease -> {
                CompletableFuture<T> result;
                try {
                    result = operation.get();
                } catch (Throwable error) {
                    lease.close();
                    return CompletableFuture.failedFuture(error);
                }
                return result.whenComplete((ignored, error) -> lease.close());
            });
        }
    }

    private static long loadLogIntervalMillis() {
        long seconds = Long.getLong("slg.scene.load-log-seconds", DEFAULT_LOAD_LOG_SECONDS);
        if (seconds <= 0L) {
            return 0L;
        }
        return Math.min(TimeUnit.DAYS.toMillis(1), TimeUnit.SECONDS.toMillis(seconds));
    }

    private static int positiveIntProperty(String propertyName, int defaultValue) {
        return Math.max(1, Integer.getInteger(propertyName, defaultValue));
    }
}
