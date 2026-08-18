package ly.sceneserver.common;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/** CPU 密集型寻路专用平台线程池，不占用 SceneShard Tick 或虚拟 IO 线程。 */
public final class ScenePathService implements AutoCloseable {
    /** 固定大小的平台线程池；A* 是 CPU 密集任务，不使用无限虚拟线程并发。 */
    private final ThreadPoolExecutor executor;
    /** 所有工作线程共享无状态算法和静态 Region 图，搜索工作区由 ThreadLocal 隔离。 */
    private final ScenePathfinder pathfinder;
    /** 配置线程数，同时作为线程池忙碌率计算的分母。 */
    private final int threadCount;
    /** 以下累计计数只用于观测，不参与业务正确性判断。 */
    private final LongAdder submittedTasks = new LongAdder();
    private final LongAdder finishedTasks = new LongAdder();
    private final LongAdder failedTasks = new LongAdder();
    private final LongAdder rejectedTasks = new LongAdder();
    private final LongAdder totalTaskNanos = new LongAdder();
    private final AtomicLong intervalMaxTaskNanos = new AtomicLong();
    /**
     * 已完成 Region 建图的静态地图及其 regionSize。
     *
     * <p>IdentityHashMap 按对象身份区分地图；即使两张地图尺寸和内容相同，也不能错误共用
     * readiness 状态。实际 Region 图由 ScenePathfinder 自己缓存。
     */
    private final Map<SceneStaticMap, Integer> preparedMaps =
            Collections.synchronizedMap(new IdentityHashMap<>());

    public ScenePathService() {
        // 每个工作线程会为 100 万格地图复用一组 int[]；默认上限 4，避免高核机器放大内存。
        int defaultThreads = Math.max(1, Math.min(4, Runtime.getRuntime().availableProcessors() / 2));
        this.threadCount = Math.max(1, Integer.getInteger("slg.scene.path.threads", defaultThreads));
        this.executor = new ThreadPoolExecutor(
                threadCount,
                threadCount,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                Thread.ofPlatform().daemon(true).name("ScenePath-CPU-", 0).factory());
        this.pathfinder = new ScenePathfinder(SceneTerrainCostProvider.defaults());
    }

    /** 启动阶段根据最终静态地图扫描 Region 边界 Portal，并缓存约 1024 个块的连通图。 */
    public void prepareMap(SceneStaticMap map, int regionSize) {
        // 必须在静态地图全部加载并冻结后调用。扫描工作在启动线程完成，不占用 Tick 和寻路线程池。
        long startedNanos = System.nanoTime();
        SceneRegionGraph graph = pathfinder.prepare(map, regionSize);
        preparedMaps.put(map, regionSize);
        ly.LoggerDef.SystemLogger.info(
                "Scene region graph prepared, width={}, height={}, regionSize={}, regions={}, traversableRegions={}, portalRuns={}, cost={}ms",
                map.width(), map.height(), regionSize, graph.regionCount(),
                graph.traversableRegionCount(), graph.portalRunCount(),
                TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startedNanos));
    }

    public CompletableFuture<ScenePathResult> findPathAsync(
            SceneStaticMap map,
            ScenePathRequest request,
            SceneVisibilitySnapshot visibility) {
        // 禁止“第一个玩家请求”偷偷在 CPU 线程里扫描整张地图，也能阻止错误 regionSize 的请求。
        Integer preparedRegionSize = preparedMaps.get(map);
        if (preparedRegionSize == null || preparedRegionSize != visibility.regionSize()) {
            return CompletableFuture.failedFuture(new IllegalStateException(
                    "scene region graph was not prepared for current static map"));
        }
        // 从这里开始才计入寻路线程池负载；准备阶段失败不属于玩家寻路任务。
        submittedTasks.increment();
        try {
            return CompletableFuture.supplyAsync(() -> {
                // 地形数组、Region 图和迷雾快照均为只读；该线程绝不修改 SceneShard 动态对象。
                long startedNanos = System.nanoTime();
                try {
                    return pathfinder.find(map, request, visibility);
                } catch (RuntimeException | Error error) {
                    failedTasks.increment();
                    throw error;
                } finally {
                    long durationNanos = Math.max(0L, System.nanoTime() - startedNanos);
                    totalTaskNanos.add(durationNanos);
                    intervalMaxTaskNanos.accumulateAndGet(durationNanos, Math::max);
                    finishedTasks.increment();
                }
            }, executor);
        } catch (RejectedExecutionException error) {
            // 线程池关闭或拒绝任务时通过失败 Future 返回，RPC 层不能把它当成“地图无路”。
            rejectedTasks.increment();
            return CompletableFuture.failedFuture(error);
        }
    }

    /** 返回线程池负载快照，并重置本日志周期的单任务耗时峰值。 */
    public ScenePathLoadSnapshot loadSnapshotAndResetPeak() {
        // 累计值保持单调，只有“本采样周期最大耗时”在读取后归零。
        return new ScenePathLoadSnapshot(
                System.nanoTime(),
                threadCount,
                executor.getPoolSize(),
                executor.getActiveCount(),
                executor.getLargestPoolSize(),
                executor.getQueue().size(),
                submittedTasks.sum(),
                finishedTasks.sum(),
                failedTasks.sum(),
                rejectedTasks.sum(),
                totalTaskNanos.sum(),
                intervalMaxTaskNanos.getAndSet(0L));
    }

    @Override
    public void close() {
        // SceneServer 停服时中断尚未执行完的 CPU 寻路；业务 Future 会以异常结束。
        executor.shutdownNow();
    }
}
