package ly.sceneserver.common;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.Consumer;

/**
 * 一个逻辑场景内的 Region 所有权目录。
 *
 * <p>Region 是运行期负载迁移的最小单位。正常状态下，坐标先换算成 Region 编号，再通过
 * {@code owners[]} 找到唯一 SceneShard；迁移状态下，目录仍对外公布源 Shard 为当前所有者，
 * 但新的坐标命令会进入该 Region 的暂存队列，直到目标 Shard 完成数据安装。
 *
 * <p>目录方法使用短临界区同步。临界区内只做数组读写和向 SceneShard 的无阻塞队列追加任务，
 * 不执行任何场景业务，也不等待 Tick，从而不会把迁移锁扩散到业务代码。
 */
final class SceneRegionDirectory {
    private final int regionSize;
    private final int columns;
    private final int rows;
    private final int shardCount;
    /** 每个 Region 当前稳定所有者的 SceneShard 下标。 */
    private final AtomicIntegerArray owners;
    /** 每次成功发起迁移时递增，用于拒绝迟到的旧迁移任务。 */
    private final long[] versions;
    /** 非 null 表示该 Region 正处于冻结、复制或切换阶段。 */
    private final MigrationSlot[] migrations;

    /**
     * 跨全部 Shard 的聚合操作数量，例如 AOI 快照和个人迷雾汇总。
     *
     * <p>这类操作必须看到迁移前或迁移后的完整状态，不能在源端已导出、目标端尚未接管的窗口
     * 执行。因此迁移开始前会等待已有聚合操作完成，迁移期间的新聚合操作则异步排队。
     */
    private int activeAggregateOperations;
    private boolean migrationPending;
    private boolean migrationActive;
    private final ArrayDeque<CompletableFuture<AggregateLease>> aggregateWaiters = new ArrayDeque<>();

    SceneRegionDirectory(SceneConfig config) {
        this.regionSize = config.regionSize();
        this.columns = (config.width() + regionSize - 1) / regionSize;
        this.rows = (config.height() + regionSize - 1) / regionSize;
        this.shardCount = config.shardCount();
        this.owners = new AtomicIntegerArray(columns * rows);
        this.versions = new long[owners.length()];
        this.migrations = new MigrationSlot[owners.length()];

        // X 轴条带只负责初始分配。Region 一旦初始化完成，后续路由只查询 owners[]，
        // 因而热点块可以迁到任意 Shard，而无需改地图坐标或复制静态地图。
        for (int regionY = 0; regionY < rows; regionY++) {
            for (int regionX = 0; regionX < columns; regionX++) {
                int centerX = Math.min(
                        config.width() - 1,
                        regionX * regionSize + regionSize / 2);
                owners.set(regionY * columns + regionX, Math.min(
                        shardCount - 1,
                        centerX * shardCount / config.width()));
            }
        }
    }

    int regionSize() {
        return regionSize;
    }

    int columns() {
        return columns;
    }

    int rows() {
        return rows;
    }

    int regionCount() {
        return owners.length();
    }

    int regionIndex(int x, int y) {
        return (y / regionSize) * columns + (x / regionSize);
    }

    int ownerShardIndex(int regionIndex) {
        requireRegion(regionIndex);
        // SceneShard.owns() 和坐标路由属于高频路径，使用 AtomicIntegerArray 保证切换可见性，
        // 同时避免每次格子操作都争抢迁移状态机使用的 synchronized 目录锁。
        return owners.get(regionIndex);
    }

    synchronized boolean isMigrating(int regionIndex) {
        requireRegion(regionIndex);
        return migrations[regionIndex] != null;
    }

    /**
     * 按 Region 顺序投递坐标命令。
     *
     * <p>稳定状态下直接进入所有者 Shard 的 FIFO；迁移状态下保存原 action 和 Future。切换成功
     * 后这些命令按到达顺序进入目标 Shard，回滚时则按相同顺序回到源 Shard，因此调用方不需要
     * 感知一次短暂的迁移窗口。
     */
    synchronized CompletableFuture<Void> dispatch(
            int regionIndex,
            SceneShard[] shards,
            Consumer<SceneShard> action) {
        requireRegion(regionIndex);
        Objects.requireNonNull(action, "action");
        MigrationSlot migration = migrations[regionIndex];
        if (migration == null) {
            // submit 只向 ConcurrentLinkedQueue 追加任务，不会在当前锁内执行业务 action。
            return shards[owners.get(regionIndex)].submit(action);
        }
        CompletableFuture<Void> result = new CompletableFuture<>();
        migration.pendingCommands.add(new PendingCommand(action, result));
        return result;
    }

    /**
     * 取得跨 Shard 聚合操作租约。迁移期间返回未完成 Future，不阻塞 RPC/网络线程。
     */
    synchronized CompletableFuture<AggregateLease> acquireAggregateLease() {
        if (!migrationPending && !migrationActive) {
            activeAggregateOperations++;
            return CompletableFuture.completedFuture(new AggregateLease(this));
        }
        CompletableFuture<AggregateLease> waiter = new CompletableFuture<>();
        aggregateWaiters.addLast(waiter);
        return waiter;
    }

    /**
     * 在专用迁移线程中冻结一个 Region 的路由。
     *
     * <p>先设置 migrationPending，阻止新的聚合操作插队；然后只等待已经发出的聚合读取完成。
     * 坐标命令不需要等待，它们会在 MigrationSlot 中暂存。
     */
    synchronized SceneRegionMigrationTicket beginMigration(
            int regionIndex,
            int targetShardIndex) throws InterruptedException {
        requireRegion(regionIndex);
        requireShard(targetShardIndex);
        int sourceShardIndex = owners.get(regionIndex);
        if (sourceShardIndex == targetShardIndex) {
            throw new IllegalArgumentException(
                    "region " + regionIndex + " already belongs to shard " + targetShardIndex);
        }
        if (migrations[regionIndex] != null || migrationPending || migrationActive) {
            throw new IllegalStateException("another region migration is already active");
        }

        migrationPending = true;
        try {
            while (activeAggregateOperations > 0) {
                wait();
            }
            long version = ++versions[regionIndex];
            SceneRegionMigrationTicket ticket = new SceneRegionMigrationTicket(
                    regionIndex, sourceShardIndex, targetShardIndex, version);
            migrations[regionIndex] = new MigrationSlot(ticket);
            migrationActive = true;
            return ticket;
        } finally {
            migrationPending = false;
            // InterruptedException 发生在真正创建 MigrationSlot 之前时，需要立即放行聚合请求。
            if (!migrationActive) {
                grantAggregateWaiters();
            }
        }
    }

    synchronized boolean matches(SceneRegionMigrationTicket ticket) {
        MigrationSlot slot = migrations[ticket.regionIndex()];
        return slot != null && slot.ticket.equals(ticket);
    }

    /**
     * 完成所有权切换，并把冻结期间的命令依次投递到目标 Shard。
     *
     * <p>目标 Region 数据必须在调用本方法前已经安装。目录锁内先追加全部暂存命令和一个 FIFO
     * 屏障，再开放新的聚合操作，保证迁移后的第一次快照不会越过迁移期间积压的命令。
     */
    synchronized Cutover commitAndFlush(
            SceneRegionMigrationTicket ticket,
            SceneShard[] shards) {
        MigrationSlot slot = requireMigration(ticket);
        owners.set(ticket.regionIndex(), ticket.targetShardIndex());
        migrations[ticket.regionIndex()] = null;
        flushPending(slot, shards[ticket.targetShardIndex()]);
        CompletableFuture<Void> barrier = shards[ticket.targetShardIndex()].submit(ignored -> {
            // 空任务只充当目标 Shard FIFO 屏障。
        });
        migrationActive = false;
        List<CompletableFuture<AggregateLease>> waiters = collectAggregateWaiters();
        notifyAll();
        return new Cutover(barrier, waiters);
    }

    /** 数据安装失败时保持源所有权，并把暂存命令重新释放给源 Shard。 */
    synchronized Cutover abortAndFlush(
            SceneRegionMigrationTicket ticket,
            SceneShard[] shards) {
        MigrationSlot slot = requireMigration(ticket);
        migrations[ticket.regionIndex()] = null;
        flushPending(slot, shards[ticket.sourceShardIndex()]);
        CompletableFuture<Void> barrier = shards[ticket.sourceShardIndex()].submit(ignored -> {
            // 确保回滚前暂存的命令已经全部执行后，再向迁移调用方报告失败。
        });
        migrationActive = false;
        List<CompletableFuture<AggregateLease>> waiters = collectAggregateWaiters();
        notifyAll();
        return new Cutover(barrier, waiters);
    }

    private void flushPending(MigrationSlot slot, SceneShard destination) {
        for (PendingCommand pending : slot.pendingCommands) {
            destination.submit(pending.action).whenComplete((ignored, error) -> {
                if (error == null) {
                    pending.future.complete(null);
                } else {
                    pending.future.completeExceptionally(error);
                }
            });
        }
        slot.pendingCommands.clear();
    }

    private MigrationSlot requireMigration(SceneRegionMigrationTicket ticket) {
        requireRegion(ticket.regionIndex());
        MigrationSlot slot = migrations[ticket.regionIndex()];
        if (slot == null || !slot.ticket.equals(ticket)) {
            throw new IllegalStateException("region migration ticket is stale: " + ticket);
        }
        return slot;
    }

    private synchronized void releaseAggregateLease() {
        if (activeAggregateOperations <= 0) {
            throw new IllegalStateException("aggregate lease released more than once");
        }
        activeAggregateOperations--;
        if (activeAggregateOperations == 0) {
            notifyAll();
        }
    }

    /** 只在持有目录锁时调用；Future 完成后的业务可能同步回调，但 synchronized 是可重入的。 */
    private void grantAggregateWaiters() {
        List<CompletableFuture<AggregateLease>> waiters = collectAggregateWaiters();
        for (CompletableFuture<AggregateLease> waiter : waiters) {
            completeAggregateWaiter(waiter);
        }
    }

    private void completeAggregateWaiter(CompletableFuture<AggregateLease> waiter) {
        AggregateLease lease = new AggregateLease(this);
        if (!waiter.complete(lease)) {
            // waiter 可能在离开目录锁后被调用方取消。此时必须归还预留的 active 计数，
            // 否则下一次 Region 迁移会永久等待一个实际上不存在的聚合操作。
            lease.close();
        }
    }

    /**
     * 在目录锁内为等待者预留 active 计数，但不完成 Future。
     *
     * <p>正常切换会把返回值交给迁移线程完成，避免上万个 AOI 回调在目标 Tick 内同步执行。
     */
    private List<CompletableFuture<AggregateLease>> collectAggregateWaiters() {
        if (migrationPending || migrationActive) {
            return List.of();
        }
        ArrayList<CompletableFuture<AggregateLease>> result = new ArrayList<>(aggregateWaiters.size());
        while (!aggregateWaiters.isEmpty()) {
            CompletableFuture<AggregateLease> waiter = aggregateWaiters.removeFirst();
            if (waiter.isCancelled()) {
                continue;
            }
            activeAggregateOperations++;
            result.add(waiter);
        }
        return result;
    }

    private void requireRegion(int regionIndex) {
        if (regionIndex < 0 || regionIndex >= owners.length()) {
            throw new IndexOutOfBoundsException("region index out of bounds: " + regionIndex);
        }
    }

    private void requireShard(int shardIndex) {
        if (shardIndex < 0 || shardIndex >= shardCount) {
            throw new IndexOutOfBoundsException("shard index out of bounds: " + shardIndex);
        }
    }

    /** 跨 Shard 聚合操作结束时释放，不绑定具体线程，因此适合 CompletableFuture 流程。 */
    static final class AggregateLease implements AutoCloseable {
        private final SceneRegionDirectory directory;
        private boolean closed;

        private AggregateLease(SceneRegionDirectory directory) {
            this.directory = directory;
        }

        @Override
        public void close() {
            if (!closed) {
                closed = true;
                directory.releaseAggregateLease();
            }
        }
    }

    /**
     * 目录切换产生的后续动作。
     *
     * <p>barrier 已在目录锁内排到暂存命令之后；等待的聚合请求由迁移线程释放，避免在目标
     * SceneShard Tick 内运行 CompletableFuture 回调。
     */
    final class Cutover {
        private final CompletableFuture<Void> barrier;
        private final List<CompletableFuture<AggregateLease>> aggregateWaiters;
        private boolean released;

        private Cutover(
                CompletableFuture<Void> barrier,
                List<CompletableFuture<AggregateLease>> aggregateWaiters) {
            this.barrier = barrier;
            this.aggregateWaiters = List.copyOf(aggregateWaiters);
        }

        CompletableFuture<Void> barrier() {
            return barrier;
        }

        synchronized void releaseAggregateWaiters() {
            if (released) {
                return;
            }
            released = true;
            for (CompletableFuture<AggregateLease> waiter : aggregateWaiters) {
                completeAggregateWaiter(waiter);
            }
        }
    }

    private static final class MigrationSlot {
        private final SceneRegionMigrationTicket ticket;
        private final List<PendingCommand> pendingCommands = new ArrayList<>();

        private MigrationSlot(SceneRegionMigrationTicket ticket) {
            this.ticket = ticket;
        }
    }

    private record PendingCommand(
            Consumer<SceneShard> action,
            CompletableFuture<Void> future) {
    }
}

/** 一次 Region 迁移的不可变 fencing token，用版本号拒绝迟到任务。 */
record SceneRegionMigrationTicket(
        int regionIndex,
        int sourceShardIndex,
        int targetShardIndex,
        long version) {
}
