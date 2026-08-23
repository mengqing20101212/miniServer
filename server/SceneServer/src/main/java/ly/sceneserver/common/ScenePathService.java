package ly.sceneserver.common;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * Java 25 虚拟线程版 A* 服务。
 *
 * <p>每个异步请求使用自己的虚拟线程，等待迷雾快照、CPU 工作区以及 SceneShard Tick 时都会
 * 自动挂起，不占用固定平台工作线程。A* 本身是 CPU 密集任务，虚拟线程不能提升 CPU 吞吐量，
 * 因此本服务仍用有界的可复用 Workspace 控制真实并行度。</p>
 *
 * <p>Workspace 内包含最多四组百万格 int[]。它们必须复用，不能放进每个短生命周期虚拟线程
 * 的 ThreadLocal；否则 1 万个并发请求会产生不可接受的内存和 GC 压力。</p>
 */
public final class ScenePathService implements AutoCloseable {
    private static final int DEFAULT_MAX_PENDING_TASKS = 10_000;
    private static final long CLOSE_WAIT_SECONDS = 10L;

    /** 无状态寻路算法；Region 图是只读缓存，搜索可变数据全部位于借出的 Workspace。 */
    private final ScenePathfinder pathfinder;
    /** 工作区数量就是 A* 的真实 CPU 并行上限。 */
    private final int maxParallelism;
    /** 从迷雾快照到结果回投期间的请求总量上限，防止无限创建并挂起虚拟线程。 */
    private final int maxPendingTasks;
    private final ArrayBlockingQueue<ScenePathfinder.Workspace> availableWorkspaces;
    private final Semaphore pendingSlots;
    private final ThreadFactory virtualThreadFactory = Thread.ofVirtual()
            .name("ScenePath-Virtual-", 0)
            .factory();
    /** close 时用于中断等待工作区或正在计算的虚拟线程，不是线程池。 */
    private final Set<Thread> liveThreads = ConcurrentHashMap.newKeySet();
    private final AtomicBoolean closed = new AtomicBoolean();
    private final AtomicInteger waitingTasks = new AtomicInteger();
    private final AtomicInteger activeSearches = new AtomicInteger();
    private final AtomicInteger intervalPeakActiveSearches = new AtomicInteger();

    /** 以下累计计数只用于观测，不参与业务正确性判断。 */
    private final LongAdder submittedTasks = new LongAdder();
    private final LongAdder finishedTasks = new LongAdder();
    private final LongAdder failedTasks = new LongAdder();
    private final LongAdder rejectedTasks = new LongAdder();
    private final LongAdder totalTaskNanos = new LongAdder();
    private final AtomicLong intervalMaxTaskNanos = new AtomicLong();

    /** 已完成 Region 建图的静态地图及其 regionSize，按地图对象身份隔离。 */
    private final Map<SceneStaticMap, Integer> preparedMaps =
            Collections.synchronizedMap(new IdentityHashMap<>());

    public ScenePathService() {
        // 每个 Workspace 在 100 万格地图上约持有四组 int[]，默认最多 4 份以限制常驻内存。
        int defaultParallelism = Math.max(1,
                Math.min(4, Runtime.getRuntime().availableProcessors() / 2));
        this.maxParallelism = Math.max(1,
                Integer.getInteger("slg.scene.path.parallelism",
                        Integer.getInteger("slg.scene.path.threads", defaultParallelism)));
        this.maxPendingTasks = Math.max(maxParallelism,
                Integer.getInteger("slg.scene.path.max-pending", DEFAULT_MAX_PENDING_TASKS));
        this.availableWorkspaces = new ArrayBlockingQueue<>(maxParallelism);
        for (int index = 0; index < maxParallelism; index++) {
            availableWorkspaces.add(new ScenePathfinder.Workspace());
        }
        this.pendingSlots = new Semaphore(maxPendingTasks, true);
        this.pathfinder = new ScenePathfinder(SceneTerrainCostProvider.defaults());
    }

    /** 启动阶段根据最终静态地图扫描 Region 边界 Portal，并缓存约 1024 个块的连通图。 */
    public void prepareMap(SceneStaticMap map, int regionSize) {
        // 必须在静态地图全部加载并冻结后调用；首个玩家请求不能承担百万格扫描。
        long startedNanos = System.nanoTime();
        SceneRegionGraph graph = pathfinder.prepare(map, regionSize);
        preparedMaps.put(map, regionSize);
        ly.LoggerDef.SystemLogger.info(
                "Scene region graph prepared, width={}, height={}, regionSize={}, regions={}, traversableRegions={}, portalRuns={}, cost={}ms",
                map.width(), map.height(), regionSize, graph.regionCount(),
                graph.traversableRegionCount(), graph.portalRunCount(),
                TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startedNanos));
    }

    /**
     * 在当前线程中完成一次受控 A*。
     *
     * <p>SceneRuntime 从虚拟线程调用本方法。工作区繁忙时当前虚拟线程会停放在 BlockingQueue，
     * 不占用 carrier 平台线程；拿到工作区后才开始消耗 CPU。</p>
     */
    ScenePathResult findPath(
            SceneStaticMap map,
            ScenePathRequest request,
            SceneVisibilitySnapshot visibility) throws InterruptedException {
        try (PathReservation reservation = reserve(map, visibility.regionSize())) {
            return findPathReserved(map, request, visibility);
        }
    }

    /**
     * 在创建完整寻路虚拟线程之前取得容量名额。
     *
     * <p>SceneRuntime 会在 RPC stripe 上调用本方法；这里只做无阻塞 tryAcquire。达到上限时
     * 立即返回拒绝，不能先创建一个虚拟线程再让它排队，否则攻击流量仍会制造无界任务。</p>
     */
    PathReservation reserve(SceneStaticMap map, int regionSize) {
        requirePreparedMap(map, regionSize);
        if (closed.get() || !pendingSlots.tryAcquire()) {
            rejectedTasks.increment();
            throw new RejectedExecutionException(
                    closed.get() ? "scene path service is closed" : "scene path pending limit reached");
        }
        return new PathReservation(this);
    }

    /** 调用方已持有全流程容量名额；这里只等待工作区并执行真正的 CPU 搜索。 */
    ScenePathResult findPathReserved(
            SceneStaticMap map,
            ScenePathRequest request,
            SceneVisibilitySnapshot visibility) throws InterruptedException {
        requirePreparedMap(map, visibility.regionSize());
        submittedTasks.increment();

        Thread currentThread = Thread.currentThread();
        liveThreads.add(currentThread);
        ScenePathfinder.Workspace workspace = null;
        long startedNanos = 0L;
        waitingTasks.incrementAndGet();
        try {
            // poll() 在虚拟线程上只会挂起该请求。短周期复查 closed 可覆盖 close() 与
            // liveThreads.add() 极窄的竞争窗口，保证停服时不会漏掉一个永久等待者。
            while (workspace == null) {
                if (closed.get()) {
                    throw new InterruptedException("scene path service is stopping");
                }
                workspace = availableWorkspaces.poll(100L, TimeUnit.MILLISECONDS);
            }
            waitingTasks.decrementAndGet();
            int active = activeSearches.incrementAndGet();
            intervalPeakActiveSearches.accumulateAndGet(active, Math::max);
            startedNanos = System.nanoTime();
            return pathfinder.find(map, request, visibility, workspace);
        } catch (RuntimeException | Error error) {
            failedTasks.increment();
            throw error;
        } catch (InterruptedException error) {
            failedTasks.increment();
            throw error;
        } finally {
            if (workspace != null) {
                long durationNanos = Math.max(0L, System.nanoTime() - startedNanos);
                totalTaskNanos.add(durationNanos);
                intervalMaxTaskNanos.accumulateAndGet(durationNanos, Math::max);
                activeSearches.decrementAndGet();
                // 工作区没有逃逸到返回结果，可以立即交给下一个挂起的虚拟线程复用。
                availableWorkspaces.offer(workspace);
            } else {
                // 只有 take() 被中断时才会走这里，需要撤销等待计数。
                waitingTasks.decrementAndGet();
            }
            liveThreads.remove(currentThread);
            finishedTasks.increment();
        }
    }

    /**
     * Future 兼容入口。新建的是“一任务一虚拟线程”，不是复用固定工作线程的线程池。
     * SceneRuntime 的完整寻路流程会直接使用同步入口，避免多套虚拟线程嵌套。
     */
    public CompletableFuture<ScenePathResult> findPathAsync(
            SceneStaticMap map,
            ScenePathRequest request,
            SceneVisibilitySnapshot visibility) {
        CompletableFuture<ScenePathResult> result = new CompletableFuture<>();
        PathReservation reservation;
        try {
            // 在创建虚拟线程之前抢占名额，拒绝流量不会产生瞬时虚拟线程风暴。
            reservation = reserve(map, visibility.regionSize());
        } catch (RuntimeException error) {
            return CompletableFuture.failedFuture(error);
        }
        Thread thread;
        try {
            thread = virtualThreadFactory.newThread(() -> {
                try (reservation) {
                    result.complete(findPathReserved(map, request, visibility));
                } catch (InterruptedException error) {
                    Thread.currentThread().interrupt();
                    result.completeExceptionally(error);
                } catch (Throwable error) {
                    result.completeExceptionally(error);
                }
            });
        } catch (RuntimeException | Error error) {
            reservation.close();
            return CompletableFuture.failedFuture(error);
        }
        try {
            thread.start();
        } catch (RuntimeException | Error error) {
            reservation.close();
            result.completeExceptionally(error);
        }
        return result;
    }

    /** 返回虚拟线程和 CPU 工作区负载快照，并重置本日志周期峰值。 */
    public ScenePathLoadSnapshot loadSnapshotAndResetPeak() {
        int active = activeSearches.get();
        return new ScenePathLoadSnapshot(
                System.nanoTime(),
                maxParallelism,
                maxPendingTasks - pendingSlots.availablePermits(),
                liveThreads.size(),
                active,
                Math.max(active, intervalPeakActiveSearches.getAndSet(active)),
                waitingTasks.get(),
                maxPendingTasks,
                submittedTasks.sum(),
                finishedTasks.sum(),
                failedTasks.sum(),
                rejectedTasks.sum(),
                totalTaskNanos.sum(),
                intervalMaxTaskNanos.getAndSet(0L));
    }

    @Override
    public void close() {
        if (!closed.compareAndSet(false, true)) {
            return;
        }
        // 等待工作区的虚拟线程和正在 A* 循环中的线程都会响应中断。
        for (Thread thread : liveThreads) {
            thread.interrupt();
        }
        long deadlineNanos = System.nanoTime() + TimeUnit.SECONDS.toNanos(CLOSE_WAIT_SECONDS);
        while (!liveThreads.isEmpty() && System.nanoTime() < deadlineNanos) {
            try {
                Thread.sleep(1L);
            } catch (InterruptedException error) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void requirePreparedMap(SceneStaticMap map, int regionSize) {
        Integer preparedRegionSize = preparedMaps.get(map);
        if (preparedRegionSize == null || preparedRegionSize != regionSize) {
            throw new IllegalStateException(
                    "scene region graph was not prepared for current static map");
        }
    }

    /**
     * 一个完整寻路流程的容量令牌。
     *
     * <p>令牌可以在 RPC stripe 取得、在寻路虚拟线程释放；AtomicBoolean 保证启动失败、
     * 中断和正常完成等路径即使重复 close 也只归还一次。</p>
     */
    static final class PathReservation implements AutoCloseable {
        private final ScenePathService owner;
        private final AtomicBoolean released = new AtomicBoolean();

        private PathReservation(ScenePathService owner) {
            this.owner = owner;
        }

        @Override
        public void close() {
            if (released.compareAndSet(false, true)) {
                owner.pendingSlots.release();
            }
        }
    }
}
