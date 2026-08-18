package ly.sceneserver.common;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;

import ly.sceneserver.common.march.SceneMarchSnapshot;
import ly.sceneserver.common.march.SceneMarchState;
import ly.sceneserver.common.march.SceneRallySnapshot;
import ly.sceneserver.common.march.SceneRallyState;

/**
 * 同一 SceneServer JVM 内的一块逻辑分片。
 *
 * <p>X 轴条带只用于启动时分配 Region；运行期由 SceneRegionDirectory 维护每个 Region 的
 * 唯一所有者。一个 SceneShard 只修改自己拥有 Region 内的动态对象，跨分片操作必须通过
 * SceneRuntime.SceneInstance.submit 投递，不能直接跨线程读写对象。
 */
public final class SceneShard {
    private final SceneConfig config;
    private final SceneStaticMap staticMap;
    /** 动态 Region 所有权的唯一事实来源；热点迁移后 owns() 会立即反映新所有者。 */
    private final SceneRegionDirectory regionDirectory;
    private final int shardIndex;
    /** 仅用于描述启动时的初始 X 条带，不再作为运行期 owns() 的判断依据。 */
    private final int minX;
    private final int maxXExclusive;
    private final Queue<Runnable> inbox = new ConcurrentLinkedQueue<>();
    private final Map<Long, SceneObject> objects = new HashMap<>();
    private final Map<Integer, LinkedHashSet<Long>> occupants = new HashMap<>();
    /** AOI 块到本 SceneShard 动态对象 ID 的倒排索引。 */
    private final Map<Integer, LinkedHashSet<Long>> objectsByBlock = new HashMap<>();
    /** AOI 块到当前观察该块的玩家 ID，用于后续按脏块做增量广播。 */
    private final Map<Integer, LinkedHashSet<Long>> viewersByBlock = new HashMap<>();
    /** 当前在线玩家的相机视野订阅；玩家离开时该状态必须释放。 */
    private final Map<Long, ViewerState> viewers = new HashMap<>();
    /**
     * 每个玩家独立的历史探索块。
     *
     * <p>该状态不能和在线 ViewerState 共用生命周期：玩家断线只取消订阅，已探索迷雾仍需保留，
     * 并最终通过异步快照持久化后在重启时恢复。
     */
    private final Map<Long, BitSet> discoveredBlocksByPlayer = new HashMap<>();
    private final SceneTickListener tickListener;
    private final int slowTickMillis;
    /** 不调用 ConcurrentLinkedQueue.size()，避免积压时为了监控再做一次 O(n) 遍历。 */
    private final AtomicInteger queuedCommands = new AtomicInteger();
    private final AtomicInteger intervalPeakQueuedCommands = new AtomicInteger();
    private final AtomicLong intervalMaxTickNanos = new AtomicLong();
    private volatile long tickNumber;
    private volatile long lastTickMillis;
    private volatile long totalTickNanos;
    private volatile long lastTickNanos;
    private volatile long totalCommands;
    private volatile long slowTickCount;
    private volatile long failedTickCount;
    private volatile int loadObjectCount;
    private volatile int loadActiveViewerCount;
    private volatile int loadFogPlayerCount;
    private volatile String lastThreadName = "not-started";

    SceneShard(
            SceneConfig config,
            SceneStaticMap staticMap,
            SceneRegionDirectory regionDirectory,
            int shardIndex,
            int minX,
            int maxXExclusive,
            SceneTickListener tickListener,
            int slowTickMillis) {
        this.config = config;
        this.staticMap = staticMap;
        this.regionDirectory = regionDirectory;
        this.shardIndex = shardIndex;
        this.minX = minX;
        this.maxXExclusive = maxXExclusive;
        this.tickListener = tickListener;
        this.slowTickMillis = slowTickMillis;
    }

    public SceneConfig config() {
        return config;
    }

    public SceneStaticMap staticMap() {
        return staticMap;
    }

    public int shardIndex() {
        return shardIndex;
    }

    public int minX() {
        return minX;
    }

    public int maxXExclusive() {
        return maxXExclusive;
    }

    public long tickNumber() {
        return tickNumber;
    }

    public long lastTickMillis() {
        return lastTickMillis;
    }

    public int objectCount() {
        return objects.size();
    }

    public boolean owns(int x, int y) {
        return x >= 0
                && x < config.width()
                && y >= 0
                && y < config.height()
                && regionDirectory.ownerShardIndex(blockIndex(x, y)) == shardIndex;
    }

    /** 将业务操作投递到 SceneShard 线程，调用方不能直接修改 shard 内对象。 */
    public CompletableFuture<Void> submit(Consumer<SceneShard> action) {
        Objects.requireNonNull(action, "action");
        CompletableFuture<Void> future = new CompletableFuture<>();
        enqueue(() -> {
            try {
                action.accept(this);
                future.complete(null);
            } catch (Throwable error) {
                future.completeExceptionally(error);
            }
        });
        return future;
    }

    /** 将读取操作也投递到 SceneShard 线程，并返回不可变结果。 */
    public <T> CompletableFuture<T> query(Function<SceneShard, T> action) {
        Objects.requireNonNull(action, "action");
        CompletableFuture<T> future = new CompletableFuture<>();
        enqueue(() -> {
            try {
                future.complete(action.apply(this));
            } catch (Throwable error) {
                future.completeExceptionally(error);
            }
        });
        return future;
    }

    /** 在所属逻辑线程添加动态对象。 */
    public void addObject(SceneObject object) {
        requireShardThreadOperation(object.x(), object.y());
        if (objects.containsKey(object.objectId())) {
            throw new IllegalStateException("scene object already exists: " + object.objectId());
        }
        objects.put(object.objectId(), object);
        occupants.computeIfAbsent(cellIndex(object.x(), object.y()), ignored -> new LinkedHashSet<>())
                .add(object.objectId());
        objectsByBlock.computeIfAbsent(blockIndex(object.x(), object.y()), ignored -> new LinkedHashSet<>())
                .add(object.objectId());
    }

    /** 在所属逻辑线程移动动态对象；移动到其他分片必须由 SceneRuntime 重新路由。 */
    public void moveObject(long objectId, int x, int y) {
        SceneObject object = requireObject(objectId);
        requireShardThreadOperation(x, y);
        removeOccupant(object.x(), object.y(), objectId);
        removeBlockObject(object.x(), object.y(), objectId);
        object.moveTo(x, y);
        occupants.computeIfAbsent(cellIndex(x, y), ignored -> new LinkedHashSet<>()).add(objectId);
        objectsByBlock.computeIfAbsent(blockIndex(x, y), ignored -> new LinkedHashSet<>()).add(objectId);
    }

    /** 在所属逻辑线程删除动态对象。 */
    public SceneObject removeObject(long objectId) {
        SceneObject object = requireObject(objectId);
        objects.remove(objectId);
        removeOccupant(object.x(), object.y(), objectId);
        removeBlockObject(object.x(), object.y(), objectId);
        return object;
    }

    /**
     * 在源 SceneShard Tick 中冻结并导出一个 Region 的全部运行时状态。
     *
     * <p>RegionDirectory 在调用本方法前已经停止向该 Region 投递新命令；FIFO 中更早到达的
     * 命令会先执行，因此这里导出的就是明确的切换点状态。对象会同时从主表、格子索引和 AOI
     * 块索引删除；在线观察者和个人迷雾也按玩家一起搬走，不能只迁移 SceneObject。
     */
    SceneRegionTransfer exportRegion(SceneRegionMigrationTicket ticket) {
        if (!regionDirectory.matches(ticket) || ticket.sourceShardIndex() != shardIndex) {
            throw new IllegalStateException("invalid source migration ticket: " + ticket);
        }
        int regionIndex = ticket.regionIndex();
        if (regionDirectory.ownerShardIndex(regionIndex) != shardIndex) {
            throw new IllegalStateException("source shard no longer owns region " + regionIndex);
        }

        LinkedHashSet<Long> indexedObjectIds = objectsByBlock.get(regionIndex);
        ArrayList<SceneObject> regionObjects = new ArrayList<>(
                indexedObjectIds == null ? 0 : indexedObjectIds.size());
        if (indexedObjectIds != null) {
            for (Long objectId : indexedObjectIds) {
                SceneObject object = objects.get(objectId);
                if (object == null || blockIndex(object.x(), object.y()) != regionIndex) {
                    throw new IllegalStateException(
                            "region object index is inconsistent, region=" + regionIndex
                                    + ", objectId=" + objectId);
                }
                LinkedHashSet<Long> cellObjects = occupants.get(cellIndex(object.x(), object.y()));
                if (cellObjects == null || !cellObjects.contains(objectId)) {
                    throw new IllegalStateException(
                            "region occupant index is inconsistent, region=" + regionIndex
                                    + ", objectId=" + objectId);
                }
                regionObjects.add(object);
            }
        }
        for (SceneObject object : objects.values()) {
            if (blockIndex(object.x(), object.y()) == regionIndex
                    && (indexedObjectIds == null || !indexedObjectIds.contains(object.objectId()))) {
                throw new IllegalStateException(
                        "object is missing from region index, region=" + regionIndex
                                + ", objectId=" + object.objectId());
            }
        }

        // 迁移不是高频路径。这里扫描玩家表换取结构简单和一致性，避免为每个已探索块再维护
        // 一份高内存反向索引；后续如果迁移频率显著升高，再根据压测结果增加专用索引。
        LinkedHashSet<Long> playerIds = new LinkedHashSet<>();
        for (Map.Entry<Long, ViewerState> entry : viewers.entrySet()) {
            if (entry.getValue().visibleBlocks.get(regionIndex)) {
                playerIds.add(entry.getKey());
            }
        }
        LinkedHashSet<Long> indexedViewers = viewersByBlock.get(regionIndex);
        if (indexedViewers != null && !playerIds.containsAll(indexedViewers)) {
            throw new IllegalStateException(
                    "region viewer index is inconsistent, region=" + regionIndex);
        }
        for (Map.Entry<Long, BitSet> entry : discoveredBlocksByPlayer.entrySet()) {
            if (entry.getValue().get(regionIndex)) {
                playerIds.add(entry.getKey());
            }
        }
        ArrayList<Long> sortedPlayerIds = new ArrayList<>(playerIds);
        sortedPlayerIds.sort(Long::compareTo);
        ArrayList<SceneRegionPlayerTransfer> playerTransfers = new ArrayList<>(sortedPlayerIds.size());
        for (Long playerId : sortedPlayerIds) {
            ViewerState viewer = viewers.get(playerId);
            boolean visible = viewer != null && viewer.visibleBlocks.get(regionIndex);
            BitSet discoveredBlocks = discoveredBlocksByPlayer.get(playerId);
            boolean discovered = discoveredBlocks != null && discoveredBlocks.get(regionIndex);
            playerTransfers.add(new SceneRegionPlayerTransfer(
                    playerId,
                    visible,
                    discovered,
                    visible ? viewer.centerBlock : null,
                    visible ? viewer.viewLevel : null,
                    visible ? viewer.requestedTagMask : 0L));
        }

        // 所有一致性检查完成后才真正拆除源端状态，避免校验异常留下半导出 Region。
        objectsByBlock.remove(regionIndex);
        for (SceneObject object : regionObjects) {
            objects.remove(object.objectId());
            removeOccupant(object.x(), object.y(), object.objectId());
        }
        viewersByBlock.remove(regionIndex);
        for (SceneRegionPlayerTransfer player : playerTransfers) {
            if (player.visible()) {
                ViewerState viewer = viewers.get(player.playerId());
                viewer.visibleBlocks.clear(regionIndex);
                if (viewer.visibleBlocks.isEmpty()) {
                    viewers.remove(player.playerId());
                }
            }
            if (player.discovered()) {
                BitSet discovered = discoveredBlocksByPlayer.get(player.playerId());
                discovered.clear(regionIndex);
                if (discovered.isEmpty()) {
                    discoveredBlocksByPlayer.remove(player.playerId());
                }
            }
        }
        return new SceneRegionTransfer(ticket, regionObjects, playerTransfers);
    }

    /**
     * 在目标 SceneShard Tick 中安装迁移数据，或在失败回滚时重新安装到源 Shard。
     *
     * <p>先完整校验对象 ID、坐标和玩家元数据，再修改任何集合。安装过程只重建本 Shard 的
     * 内存索引，不做数据库 IO；持久化仍遵循场景异步投影方案。
     */
    void installRegion(SceneRegionTransfer transfer) {
        SceneRegionMigrationTicket ticket = transfer.ticket();
        if (!regionDirectory.matches(ticket)
                || (shardIndex != ticket.sourceShardIndex()
                && shardIndex != ticket.targetShardIndex())) {
            throw new IllegalStateException("invalid destination migration ticket: " + ticket);
        }
        int regionIndex = ticket.regionIndex();
        LinkedHashSet<Long> existingRegionObjects = objectsByBlock.get(regionIndex);
        if (existingRegionObjects != null && !existingRegionObjects.isEmpty()) {
            throw new IllegalStateException(
                    "destination already contains Region objects: " + regionIndex);
        }
        LinkedHashSet<Long> existingRegionViewers = viewersByBlock.get(regionIndex);
        if (existingRegionViewers != null && !existingRegionViewers.isEmpty()) {
            throw new IllegalStateException(
                    "destination already contains Region viewers: " + regionIndex);
        }
        for (ViewerState viewer : viewers.values()) {
            if (viewer.visibleBlocks.get(regionIndex)) {
                throw new IllegalStateException(
                        "destination viewer already contains Region: " + regionIndex);
            }
        }
        for (BitSet discovered : discoveredBlocksByPlayer.values()) {
            if (discovered.get(regionIndex)) {
                throw new IllegalStateException(
                        "destination fog already contains Region: " + regionIndex);
            }
        }
        LinkedHashSet<Long> transferIds = new LinkedHashSet<>();
        for (SceneObject object : transfer.objects()) {
            if (blockIndex(object.x(), object.y()) != regionIndex) {
                throw new IllegalStateException(
                        "object is outside migration region, objectId=" + object.objectId());
            }
            if (!transferIds.add(object.objectId()) || objects.containsKey(object.objectId())) {
                throw new IllegalStateException(
                        "duplicate object while installing region: " + object.objectId());
            }
        }

        for (SceneObject object : transfer.objects()) {
            objects.put(object.objectId(), object);
            occupants.computeIfAbsent(
                            cellIndex(object.x(), object.y()), ignored -> new LinkedHashSet<>())
                    .add(object.objectId());
            objectsByBlock.computeIfAbsent(regionIndex, ignored -> new LinkedHashSet<>())
                    .add(object.objectId());
        }
        for (SceneRegionPlayerTransfer player : transfer.players()) {
            if (player.visible()) {
                ViewerState viewer = viewers.computeIfAbsent(
                        player.playerId(), ignored -> new ViewerState());
                viewer.visibleBlocks.set(regionIndex);
                viewer.centerBlock = player.centerBlock();
                viewer.viewLevel = player.viewLevel();
                viewer.requestedTagMask = player.requestedTagMask();
                viewersByBlock.computeIfAbsent(regionIndex, ignored -> new LinkedHashSet<>())
                        .add(player.playerId());
            }
            if (player.discovered()) {
                discoveredBlocksByPlayer
                        .computeIfAbsent(player.playerId(), ignored -> new BitSet())
                        .set(regionIndex);
            }
        }
    }

    public SceneObject object(long objectId) {
        return objects.get(objectId);
    }

    /** 把已经冻结部队数据并完成目标校验的行军注册成地图动态对象。 */
    public void addMarch(SceneMarchState march) {
        SceneMarchSnapshot snapshot = march.snapshot();
        addObject(new SceneObject(
                snapshot.marchId(),
                SceneObjectType.MARCH,
                snapshot.ownerPlayerId(),
                snapshot.origin().x(),
                snapshot.origin().y(),
                march));
    }

    /** 把集结点注册成战略动态对象，AOI 世界层也会同步其摘要。 */
    public void addRally(SceneRallyState rally) {
        SceneRallySnapshot snapshot = rally.snapshot();
        addObject(new SceneObject(
                snapshot.rallyId(),
                SceneObjectType.RALLY,
                snapshot.allianceId(),
                snapshot.assemblyPoint().x(),
                snapshot.assemblyPoint().y(),
                rally));
    }

    /** 在 SceneShard Tick 中串行修改行军状态，并同步增加通用对象版本。 */
    public void updateMarch(long marchId, Consumer<SceneMarchState> action) {
        SceneObject object = requireObject(marchId);
        if (!(object.state() instanceof SceneMarchState march)) {
            throw new IllegalArgumentException("scene object is not a march: " + marchId);
        }
        action.accept(march);
        object.state(march);
    }

    /** 在 SceneShard Tick 中串行修改集结状态，并同步增加通用对象版本。 */
    public void updateRally(long rallyId, Consumer<SceneRallyState> action) {
        SceneObject object = requireObject(rallyId);
        if (!(object.state() instanceof SceneRallyState rally)) {
            throw new IllegalArgumentException("scene object is not a rally: " + rallyId);
        }
        action.accept(rally);
        object.state(rally);
    }

    /**
     * 推进行军到同一 SceneShard 内的新路径点。
     *
     * <p>跨 SceneShard 点会被拒绝，必须先由 SceneRuntime 协调对象所有权转移，不能直接
     * 从当前 Tick 修改另一个分片。
     */
    public void advanceMarch(long marchId, int newPathIndex) {
        SceneObject object = requireObject(marchId);
        if (!(object.state() instanceof SceneMarchState march)) {
            throw new IllegalArgumentException("scene object is not a march: " + marchId);
        }
        SceneMarchSnapshot snapshot = march.snapshot();
        if (newPathIndex < 0 || newPathIndex >= snapshot.path().size()) {
            throw new IllegalArgumentException("invalid path index: " + newPathIndex);
        }
        ScenePoint point = snapshot.path().get(newPathIndex);
        requireShardThreadOperation(point.x(), point.y());
        march.advanceTo(newPathIndex);
        moveObject(marchId, point.x(), point.y());
    }

    /** 返回格子上的对象快照，避免把内部集合暴露给其他线程。 */
    public java.util.List<SceneObject> objectsAt(int x, int y) {
        if (!owns(x, y)) {
            return java.util.List.of();
        }
        LinkedHashSet<Long> ids = occupants.get(cellIndex(x, y));
        if (ids == null || ids.isEmpty()) {
            return java.util.List.of();
        }
        ArrayList<SceneObject> result = new ArrayList<>(ids.size());
        for (Long id : ids) {
            SceneObject object = objects.get(id);
            if (object != null) {
                result.add(object);
            }
        }
        return java.util.List.copyOf(result);
    }

    /** 返回格子上的不可变对象快照，RPC 线程不会再持有可变 SceneObject。 */
    public List<SceneObjectSnapshot> objectSnapshotsAt(int x, int y) {
        List<SceneObject> source = objectsAt(x, y);
        if (source.isEmpty()) {
            return List.of();
        }
        ArrayList<SceneObjectSnapshot> result = new ArrayList<>(source.size());
        for (SceneObject object : source) {
            result.add(snapshot(object));
        }
        return List.copyOf(result);
    }

    /**
     * 更新玩家在本 SceneShard 的块订阅，并在同一个 Tick 中创建分层快照。
     *
     * <p>visibleBlocks 是全场景块编号；本分片只登记 RegionDirectory 当前分配给自己的块。
     * 每个块始终只有一个 SceneShard 负责，因此迁移后不会由源、目标重复生成 AOI 数据。
     */
    public SceneViewSnapshot updateViewer(SceneViewRequest request, BitSet visibleBlocks) {
        BitSet localVisible = localBlocks(visibleBlocks);
        ViewerState state = viewers.get(request.playerId());
        if (state == null && localVisible.isEmpty()) {
            // 不让每个玩家在所有分片都创建空状态；只在真正观察或探索过本分片时驻留。
            return new SceneViewSnapshot(
                    request.playerId(),
                    new ScenePoint(
                            request.center().x() / config.regionSize(),
                            request.center().y() / config.regionSize()),
                    request.viewLevel(),
                    tickNumber,
                    List.of(),
                    List.of(),
                    List.of());
        }
        if (state == null) {
            state = new ViewerState();
            viewers.put(request.playerId(), state);
        }
        updateViewerIndex(request.playerId(), state.visibleBlocks, localVisible);
        state.visibleBlocks = localVisible;
        discoveredBlocksByPlayer
                .computeIfAbsent(request.playerId(), ignored -> new BitSet())
                .or(localVisible);
        state.centerBlock = new ScenePoint(
                request.center().x() / config.regionSize(),
                request.center().y() / config.regionSize());
        state.viewLevel = request.viewLevel();
        state.requestedTagMask = request.requestedTagMask();
        return snapshotViewer(request.playerId(), state);
    }

    /** 玩家离开场景时只删除在线块订阅，不能清理该玩家已经解锁的战争迷雾。 */
    public void removeViewer(long playerId) {
        ViewerState state = viewers.remove(playerId);
        if (state != null) {
            updateViewerIndex(playerId, state.visibleBlocks, new BitSet());
        }
    }

    /** 为异步寻路复制一份线程安全的战争迷雾块快照。 */
    public SceneVisibilitySnapshot visibilitySnapshot(long playerId) {
        ViewerState state = viewers.get(playerId);
        BitSet visible = state == null ? new BitSet() : state.visibleBlocks;
        BitSet discovered = discoveredBlocksByPlayer.getOrDefault(playerId, new BitSet());
        return new SceneVisibilitySnapshot(
                config.regionSize(), blockColumns(), visible, discovered);
    }

    /** 恢复该玩家属于本分片的探索块；调用方传入的是全场景块编号。 */
    public void restoreDiscoveredBlocks(long playerId, BitSet discoveredBlocks) {
        if (playerId <= 0) {
            throw new IllegalArgumentException("playerId must be positive");
        }
        discoveredBlocksByPlayer.put(playerId, localBlocks(discoveredBlocks));
    }

    /** 复制该玩家在本分片的探索块，供异步持久化或跨服迁移使用。 */
    public BitSet discoveredBlocksSnapshot(long playerId) {
        BitSet discovered = discoveredBlocksByPlayer.get(playerId);
        return discovered == null ? new BitSet() : (BitSet) discovered.clone();
    }

    /**
     * 玩家迷雾已经成功持久化且不再在线时，显式回收内存。
     *
     * <p>如果玩家仍有活动视野，拒绝回收，避免把在线寻路的迷雾状态清空。
     */
    public void evictDiscoveredBlocks(long playerId) {
        if (viewers.containsKey(playerId)) {
            throw new IllegalStateException("cannot evict fog while viewer is active: " + playerId);
        }
        discoveredBlocksByPlayer.remove(playerId);
    }

    /** 测试和可观测使用：返回指定 AOI 块当前注册的玩家数量。 */
    public int viewerCountAtBlock(int blockIndex) {
        LinkedHashSet<Long> playerIds = viewersByBlock.get(blockIndex);
        return playerIds == null ? 0 : playerIds.size();
    }

    /**
     * 返回可由监控线程安全读取的负载快照，并重置本日志周期内的 Tick/队列峰值。
     *
     * <p>对象、观察者和迷雾玩家数量由 Tick 线程在每轮结束时发布，监控线程不直接读取 HashMap。
     */
    public SceneShardLoadSnapshot loadSnapshotAndResetPeaks() {
        int currentQueue = queuedCommands.get();
        int peakQueue = Math.max(currentQueue, intervalPeakQueuedCommands.getAndSet(currentQueue));
        return new SceneShardLoadSnapshot(
                config.sceneId(),
                shardIndex,
                config.tickMillis(),
                System.nanoTime(),
                tickNumber,
                lastTickMillis,
                totalTickNanos,
                lastTickNanos,
                intervalMaxTickNanos.getAndSet(lastTickNanos),
                totalCommands,
                slowTickCount,
                failedTickCount,
                currentQueue,
                peakQueue,
                loadObjectCount,
                loadActiveViewerCount,
                loadFogPlayerCount,
                lastThreadName);
    }

    /** 由 SceneRuntime 的调度线程调用；所有场景业务更新都从这里开始。 */
    void tick() {
        long startedNanos = System.nanoTime();
        int processedCommands = 0;
        lastThreadName = Thread.currentThread().getName();
        try {
            Runnable command;
            while ((command = inbox.poll()) != null) {
                queuedCommands.decrementAndGet();
                command.run();
                processedCommands++;
            }
            tickNumber++;
            lastTickMillis = System.currentTimeMillis();
            if (tickListener != null) {
                tickListener.onTick(this, tickNumber, lastTickMillis);
            }
        } catch (Throwable error) {
            // 兜住整个 Tick，避免 ScheduledExecutorService 因一次异常永久取消周期任务。
            failedTickCount++;
            ly.LoggerDef.SystemLogger.error(
                    "SceneShard tick failed, sceneId={}, shardIndex={}, tickNumber={}",
                    config.sceneId(), shardIndex, tickNumber, error);
        } finally {
            long durationNanos = Math.max(0L, System.nanoTime() - startedNanos);
            lastTickNanos = durationNanos;
            totalTickNanos += durationNanos;
            totalCommands += processedCommands;
            intervalMaxTickNanos.accumulateAndGet(durationNanos, Math::max);
            if (durationNanos >= slowTickMillis * 1_000_000L) {
                slowTickCount++;
            }
            // 这些集合只在 SceneShard 线程读取，并通过 volatile 字段发布给监控线程。
            loadObjectCount = objects.size();
            loadActiveViewerCount = viewers.size();
            loadFogPlayerCount = discoveredBlocksByPlayer.size();
        }
    }

    private void enqueue(Runnable command) {
        int queueDepth = queuedCommands.incrementAndGet();
        intervalPeakQueuedCommands.accumulateAndGet(queueDepth, Math::max);
        inbox.offer(command);
    }

    private SceneObject requireObject(long objectId) {
        SceneObject object = objects.get(objectId);
        if (object == null) {
            throw new IllegalArgumentException("scene object not found: " + objectId);
        }
        return object;
    }

    private void requireShardThreadOperation(int x, int y) {
        if (!owns(x, y)) {
            throw new IllegalArgumentException(
                    "point does not belong to shard " + shardIndex + ": " + x + "," + y);
        }
    }

    private int cellIndex(int x, int y) {
        return y * config.width() + x;
    }

    public int blockIndex(int x, int y) {
        return (y / config.regionSize()) * blockColumns() + (x / config.regionSize());
    }

    private int blockColumns() {
        return (config.width() + config.regionSize() - 1) / config.regionSize();
    }

    private boolean intersectsBlock(int blockIndex) {
        return regionDirectory.ownerShardIndex(blockIndex) == shardIndex;
    }

    private BitSet localBlocks(BitSet globalBlocks) {
        BitSet result = new BitSet();
        for (int blockIndex = globalBlocks.nextSetBit(0);
                blockIndex >= 0;
                blockIndex = globalBlocks.nextSetBit(blockIndex + 1)) {
            if (intersectsBlock(blockIndex)) {
                result.set(blockIndex);
            }
        }
        return result;
    }

    private void updateViewerIndex(long playerId, BitSet oldBlocks, BitSet newBlocks) {
        BitSet removed = (BitSet) oldBlocks.clone();
        removed.andNot(newBlocks);
        for (int blockIndex = removed.nextSetBit(0);
                blockIndex >= 0;
                blockIndex = removed.nextSetBit(blockIndex + 1)) {
            LinkedHashSet<Long> playerIds = viewersByBlock.get(blockIndex);
            if (playerIds != null) {
                playerIds.remove(playerId);
                if (playerIds.isEmpty()) {
                    viewersByBlock.remove(blockIndex);
                }
            }
        }

        BitSet added = (BitSet) newBlocks.clone();
        added.andNot(oldBlocks);
        for (int blockIndex = added.nextSetBit(0);
                blockIndex >= 0;
                blockIndex = added.nextSetBit(blockIndex + 1)) {
            viewersByBlock.computeIfAbsent(blockIndex, ignored -> new LinkedHashSet<>()).add(playerId);
        }
    }

    private SceneViewSnapshot snapshotViewer(long playerId, ViewerState state) {
        long objectFilter = state.viewLevel.effectiveObjectTagMask(state.requestedTagMask);
        ArrayList<SceneObjectSnapshot> objectSnapshots = new ArrayList<>();
        ArrayList<SceneBlockSnapshot> blockSnapshots = new ArrayList<>();

        for (int blockIndex = state.visibleBlocks.nextSetBit(0);
                blockIndex >= 0;
                blockIndex = state.visibleBlocks.nextSetBit(blockIndex + 1)) {
            LinkedHashSet<Long> objectIds = objectsByBlock.get(blockIndex);
            int objectCount = 0;
            long aggregateTags = 0L;
            if (objectIds != null) {
                for (Long objectId : objectIds) {
                    SceneObject object = objects.get(objectId);
                    if (object == null) {
                        continue;
                    }
                    objectCount++;
                    aggregateTags |= object.dataTagMask();
                    if ((object.dataTagMask() & objectFilter) != 0L) {
                        objectSnapshots.add(snapshot(object));
                    }
                }
            }
            int blockX = blockIndex % blockColumns();
            int blockY = blockIndex / blockColumns();
            blockSnapshots.add(new SceneBlockSnapshot(
                    blockIndex, blockX, blockY, true, true, objectCount, aggregateTags));
        }

        BitSet playerDiscovered = discoveredBlocksByPlayer.getOrDefault(playerId, new BitSet());
        ArrayList<Integer> discovered = new ArrayList<>(playerDiscovered.cardinality());
        for (int blockIndex = playerDiscovered.nextSetBit(0);
                blockIndex >= 0;
                blockIndex = playerDiscovered.nextSetBit(blockIndex + 1)) {
            discovered.add(blockIndex);
        }
        objectSnapshots.sort(Comparator.comparingLong(SceneObjectSnapshot::objectId));
        blockSnapshots.sort(Comparator.comparingInt(SceneBlockSnapshot::blockIndex));
        return new SceneViewSnapshot(
                playerId,
                state.centerBlock,
                state.viewLevel,
                tickNumber,
                objectSnapshots,
                blockSnapshots,
                discovered);
    }

    private SceneObjectSnapshot snapshot(SceneObject object) {
        SceneMarchSnapshot march = object.state() instanceof SceneMarchState state
                ? state.snapshot()
                : object.state() instanceof SceneMarchSnapshot state ? state : null;
        SceneRallySnapshot rally = object.state() instanceof SceneRallyState state
                ? state.snapshot()
                : object.state() instanceof SceneRallySnapshot state ? state : null;
        return new SceneObjectSnapshot(
                object.objectId(),
                object.type(),
                object.ownerId(),
                new ScenePoint(object.x(), object.y()),
                object.stateVersion(),
                object.dataTagMask(),
                march,
                rally);
    }

    private void removeOccupant(int x, int y, long objectId) {
        LinkedHashSet<Long> ids = occupants.get(cellIndex(x, y));
        if (ids == null) {
            return;
        }
        ids.remove(objectId);
        if (ids.isEmpty()) {
            occupants.remove(cellIndex(x, y));
        }
    }

    private void removeBlockObject(int x, int y, long objectId) {
        LinkedHashSet<Long> ids = objectsByBlock.get(blockIndex(x, y));
        if (ids == null) {
            return;
        }
        ids.remove(objectId);
        if (ids.isEmpty()) {
            objectsByBlock.remove(blockIndex(x, y));
        }
    }

    private static final class ViewerState {
        private BitSet visibleBlocks = new BitSet();
        private ScenePoint centerBlock = new ScenePoint(0, 0);
        private SceneViewLevel viewLevel = SceneViewLevel.DETAIL;
        private long requestedTagMask;
    }
}
