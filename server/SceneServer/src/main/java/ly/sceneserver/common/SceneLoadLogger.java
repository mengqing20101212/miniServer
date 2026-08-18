package ly.sceneserver.common;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ly.LoggerDef;

/**
 * 周期输出 SceneShard、寻路线程池和实际平台线程负载，供线上观察热点与积压。
 *
 * <p>SceneShard 任务会在调度池的平台线程之间迁移，所以同时记录“逻辑分片忙碌率”和
 * “实际线程 CPU 百分比”；只观察其中一层都无法准确定位热点场景。
 */
public final class SceneLoadLogger implements AutoCloseable {
    private static final String TICK_THREAD_PREFIX = "SceneShard-Tick-";
    private static final String PATH_THREAD_PREFIX = "ScenePath-CPU-";
    private static final String MIGRATION_THREAD_PREFIX = "SceneRegion-Migration-";

    private final SceneRuntime runtime;
    private final ScenePathService pathService;
    private final SceneRegionMigrationService regionMigrationService;
    private final long intervalMillis;
    private final int slowTickMillis;
    private final int queueWarnThreshold;
    private final ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
    private final Map<String, SceneShardLoadSnapshot> previousShards = new HashMap<>();
    private final Map<Long, ThreadSample> previousThreads = new HashMap<>();
    private ScheduledExecutorService executor;
    private ScenePathLoadSnapshot previousPath;
    private SceneRegionMigrationLoadSnapshot previousMigration;
    private boolean cpuTimeEnabled;

    SceneLoadLogger(
            SceneRuntime runtime,
            ScenePathService pathService,
            SceneRegionMigrationService regionMigrationService,
            long intervalMillis,
            int slowTickMillis,
            int queueWarnThreshold) {
        this.runtime = runtime;
        this.pathService = pathService;
        this.regionMigrationService = regionMigrationService;
        this.intervalMillis = intervalMillis;
        this.slowTickMillis = slowTickMillis;
        this.queueWarnThreshold = queueWarnThreshold;
        this.cpuTimeEnabled = enableThreadCpuTime();
    }

    public void start() {
        if (intervalMillis <= 0L || executor != null) {
            if (intervalMillis <= 0L) {
                LoggerDef.SystemLogger.info("Scene load logger disabled, property=slg.scene.load-log-seconds");
            }
            return;
        }
        // 先建立累计值基线，第一次周期日志就是完整一个采样窗口，不把进程历史平均进去。
        for (SceneShardLoadSnapshot snapshot : runtime.collectShardLoadSnapshots()) {
            previousShards.put(shardKey(snapshot), snapshot);
        }
        previousPath = pathService.loadSnapshotAndResetPeak();
        previousMigration = regionMigrationService.loadSnapshotAndResetPeak();
        captureThreadSamples(false);

        executor = Executors.newSingleThreadScheduledExecutor(
                Thread.ofPlatform().daemon(true).name("Scene-Load-Logger", 0).factory());
        executor.scheduleWithFixedDelay(
                this::safeLogNow,
                intervalMillis,
                intervalMillis,
                TimeUnit.MILLISECONDS);
        LoggerDef.SystemLogger.info(
                "Scene load logger started, intervalMillis={}, slowTickMillis={}, queueWarnThreshold={}, threadCpuTime={}",
                intervalMillis, slowTickMillis, queueWarnThreshold, cpuTimeEnabled);
    }

    /** 立即输出一次，后续可由 GM 命令或线上诊断入口调用。 */
    public synchronized void logNow() {
        long nowMillis = System.currentTimeMillis();
        for (SceneShardLoadSnapshot current : runtime.collectShardLoadSnapshots()) {
            String key = shardKey(current);
            SceneShardLoadSnapshot previous = previousShards.put(key, current);
            if (previous == null) {
                LoggerDef.SystemLogger.info(
                        "SceneShard load baseline, sceneId={}, shard={}, tickThread={}, tickNumber={}, lastTickMicros={}, queue={}, peakQueue={}, objects={}, activeViewers={}, fogPlayers={}, tickLagMillis={}",
                        current.sceneId(), current.shardIndex(), current.lastThreadName(),
                        current.tickNumber(), current.lastTickNanos() / 1_000L,
                        current.queuedCommands(), current.intervalPeakQueuedCommands(),
                        current.objectCount(), current.activeViewerCount(), current.fogPlayerCount(),
                        current.lastTickCompletedMillis() == 0L
                                ? -1L
                                : Math.max(0L, nowMillis - current.lastTickCompletedMillis()));
                continue;
            }
            long elapsedNanos = Math.max(1L, current.sampleNanoTime() - previous.sampleNanoTime());
            long tickDelta = Math.max(0L, current.tickNumber() - previous.tickNumber());
            long tickNanosDelta = Math.max(0L, current.totalTickNanos() - previous.totalTickNanos());
            long commandDelta = Math.max(0L, current.totalCommands() - previous.totalCommands());
            long slowTickDelta = Math.max(0L, current.slowTickCount() - previous.slowTickCount());
            long failedTickDelta = Math.max(0L, current.failedTickCount() - previous.failedTickCount());
            double busyPercent = percent(tickNanosDelta, elapsedNanos);
            double tickRate = round2(tickDelta * 1_000_000_000D / elapsedNanos);
            long averageTickMicros = tickDelta == 0L ? 0L : tickNanosDelta / tickDelta / 1_000L;
            long maxTickMicros = current.intervalMaxTickNanos() / 1_000L;
            long tickLagMillis = current.lastTickCompletedMillis() == 0L
                    ? -1L
                    : Math.max(0L, nowMillis - current.lastTickCompletedMillis());
            boolean warning = maxTickMicros >= slowTickMillis * 1_000L
                    || failedTickDelta > 0L
                    || current.intervalPeakQueuedCommands() >= queueWarnThreshold
                    || tickLagMillis > Math.max(1_000L, current.tickMillis() * 3L);

            if (warning) {
                LoggerDef.SystemLogger.warn(
                        "SceneShard load warning, sceneId={}, shard={}, tickThread={}, busyPercent={}, tickRate={}, tickDelta={}, avgTickMicros={}, maxTickMicros={}, lastTickMicros={}, slowTicksDelta={}, failedTicksDelta={}, commandsDelta={}, queue={}, peakQueue={}, objects={}, activeViewers={}, fogPlayers={}, tickLagMillis={}",
                        current.sceneId(), current.shardIndex(), current.lastThreadName(), busyPercent,
                        tickRate, tickDelta, averageTickMicros, maxTickMicros,
                        current.lastTickNanos() / 1_000L, slowTickDelta, failedTickDelta,
                        commandDelta, current.queuedCommands(), current.intervalPeakQueuedCommands(),
                        current.objectCount(), current.activeViewerCount(), current.fogPlayerCount(),
                        tickLagMillis);
            } else {
                LoggerDef.SystemLogger.info(
                        "SceneShard load, sceneId={}, shard={}, tickThread={}, busyPercent={}, tickRate={}, tickDelta={}, avgTickMicros={}, maxTickMicros={}, lastTickMicros={}, slowTicksDelta={}, failedTicksDelta={}, commandsDelta={}, queue={}, peakQueue={}, objects={}, activeViewers={}, fogPlayers={}, tickLagMillis={}",
                        current.sceneId(), current.shardIndex(), current.lastThreadName(), busyPercent,
                        tickRate, tickDelta, averageTickMicros, maxTickMicros,
                        current.lastTickNanos() / 1_000L, slowTickDelta, failedTickDelta,
                        commandDelta, current.queuedCommands(), current.intervalPeakQueuedCommands(),
                        current.objectCount(), current.activeViewerCount(), current.fogPlayerCount(),
                        tickLagMillis);
            }
        }
        logPathLoad();
        logRegionMigrationLoad();
        captureThreadSamples(true);
    }

    @Override
    public synchronized void close() {
        ScheduledExecutorService current = executor;
        executor = null;
        if (current != null) {
            current.shutdownNow();
        }
        previousShards.clear();
        previousThreads.clear();
        previousPath = null;
        previousMigration = null;
    }

    private void logPathLoad() {
        ScenePathLoadSnapshot current = pathService.loadSnapshotAndResetPeak();
        ScenePathLoadSnapshot previous = previousPath;
        previousPath = current;
        if (previous == null) {
            LoggerDef.SystemLogger.info(
                    "ScenePath pool load baseline, configuredThreads={}, poolSize={}, activeThreads={}, largestPoolSize={}, queue={}, submitted={}, finished={}, failed={}, rejected={}, maxTaskMicros={}",
                    current.configuredThreads(), current.poolSize(), current.activeThreads(),
                    current.largestPoolSize(), current.queuedTasks(), current.submittedTasks(),
                    current.finishedTasks(), current.failedTasks(), current.rejectedTasks(),
                    current.intervalMaxTaskNanos() / 1_000L);
            return;
        }
        long elapsedNanos = Math.max(1L, current.sampleNanoTime() - previous.sampleNanoTime());
        long finishedDelta = Math.max(0L, current.finishedTasks() - previous.finishedTasks());
        long failedDelta = Math.max(0L, current.failedTasks() - previous.failedTasks());
        long rejectedDelta = Math.max(0L, current.rejectedTasks() - previous.rejectedTasks());
        long taskNanosDelta = Math.max(0L, current.totalTaskNanos() - previous.totalTaskNanos());
        long averageTaskMicros = finishedDelta == 0L ? 0L : taskNanosDelta / finishedDelta / 1_000L;
        double busyPercent = percent(
                taskNanosDelta,
                elapsedNanos * Math.max(1L, current.configuredThreads()));
        boolean warning = failedDelta > 0L
                || rejectedDelta > 0L
                || current.queuedTasks() >= queueWarnThreshold;
        if (warning) {
            LoggerDef.SystemLogger.warn(
                    "ScenePath pool load warning, busyPercent={}, configuredThreads={}, poolSize={}, activeThreads={}, largestPoolSize={}, queue={}, submittedDelta={}, finishedDelta={}, failedDelta={}, rejectedDelta={}, avgTaskMicros={}, maxTaskMicros={}",
                    busyPercent, current.configuredThreads(), current.poolSize(), current.activeThreads(),
                    current.largestPoolSize(), current.queuedTasks(),
                    Math.max(0L, current.submittedTasks() - previous.submittedTasks()), finishedDelta,
                    failedDelta, rejectedDelta, averageTaskMicros,
                    current.intervalMaxTaskNanos() / 1_000L);
        } else {
            LoggerDef.SystemLogger.info(
                    "ScenePath pool load, busyPercent={}, configuredThreads={}, poolSize={}, activeThreads={}, largestPoolSize={}, queue={}, submittedDelta={}, finishedDelta={}, failedDelta={}, rejectedDelta={}, avgTaskMicros={}, maxTaskMicros={}",
                    busyPercent, current.configuredThreads(), current.poolSize(), current.activeThreads(),
                    current.largestPoolSize(), current.queuedTasks(),
                    Math.max(0L, current.submittedTasks() - previous.submittedTasks()), finishedDelta,
                    failedDelta, rejectedDelta, averageTaskMicros,
                    current.intervalMaxTaskNanos() / 1_000L);
        }
    }

    /**
     * 输出热点 Region 专用迁移线程负载。
     *
     * <p>迁移本来就应该低频；出现队列持续增长、失败或拒绝通常意味着自动均衡策略过于激进，
     * 或某个源/目标 SceneShard Tick 已经严重阻塞，需要告警而不是继续并发搬运更多 Region。
     */
    private void logRegionMigrationLoad() {
        SceneRegionMigrationLoadSnapshot current = regionMigrationService.loadSnapshotAndResetPeak();
        SceneRegionMigrationLoadSnapshot previous = previousMigration;
        previousMigration = current;
        if (previous == null) {
            LoggerDef.SystemLogger.info(
                    "SceneRegion migration load baseline, poolSize={}, activeThreads={}, queue={}, submitted={}, finished={}, succeeded={}, failed={}, rejected={}, maxTaskMicros={}",
                    current.poolSize(), current.activeThreads(), current.queuedTasks(),
                    current.submittedTasks(), current.finishedTasks(), current.succeededTasks(),
                    current.failedTasks(), current.rejectedTasks(),
                    current.intervalMaxTaskNanos() / 1_000L);
            return;
        }
        long finishedDelta = Math.max(0L, current.finishedTasks() - previous.finishedTasks());
        long succeededDelta = Math.max(0L, current.succeededTasks() - previous.succeededTasks());
        long failedDelta = Math.max(0L, current.failedTasks() - previous.failedTasks());
        long rejectedDelta = Math.max(0L, current.rejectedTasks() - previous.rejectedTasks());
        long taskNanosDelta = Math.max(0L, current.totalTaskNanos() - previous.totalTaskNanos());
        long averageTaskMicros = finishedDelta == 0L
                ? 0L
                : taskNanosDelta / finishedDelta / 1_000L;
        boolean warning = failedDelta > 0L || rejectedDelta > 0L || current.queuedTasks() > 0;
        if (warning) {
            LoggerDef.SystemLogger.warn(
                    "SceneRegion migration load warning, poolSize={}, activeThreads={}, queue={}, submittedDelta={}, finishedDelta={}, succeededDelta={}, failedDelta={}, rejectedDelta={}, avgTaskMicros={}, maxTaskMicros={}",
                    current.poolSize(), current.activeThreads(), current.queuedTasks(),
                    Math.max(0L, current.submittedTasks() - previous.submittedTasks()),
                    finishedDelta, succeededDelta, failedDelta, rejectedDelta, averageTaskMicros,
                    current.intervalMaxTaskNanos() / 1_000L);
        } else {
            LoggerDef.SystemLogger.info(
                    "SceneRegion migration load, poolSize={}, activeThreads={}, queue={}, submittedDelta={}, finishedDelta={}, succeededDelta={}, failedDelta={}, rejectedDelta={}, avgTaskMicros={}, maxTaskMicros={}",
                    current.poolSize(), current.activeThreads(), current.queuedTasks(),
                    Math.max(0L, current.submittedTasks() - previous.submittedTasks()),
                    finishedDelta, succeededDelta, failedDelta, rejectedDelta, averageTaskMicros,
                    current.intervalMaxTaskNanos() / 1_000L);
        }
    }

    private void captureThreadSamples(boolean writeLog) {
        long sampleNanos = System.nanoTime();
        long[] threadIds = threadBean.getAllThreadIds();
        ThreadInfo[] infos = threadBean.getThreadInfo(threadIds, 0);
        List<ThreadInfo> managedThreads = new ArrayList<>();
        if (infos != null) {
            for (ThreadInfo info : infos) {
                if (info != null && isManagedThread(info.getThreadName())) {
                    managedThreads.add(info);
                }
            }
        }
        managedThreads.sort(Comparator.comparing(ThreadInfo::getThreadName));
        Set<Long> activeIds = new HashSet<>();
        for (ThreadInfo info : managedThreads) {
            long threadId = info.getThreadId();
            activeIds.add(threadId);
            long cpuNanos = cpuTimeEnabled ? threadBean.getThreadCpuTime(threadId) : -1L;
            ThreadSample current = new ThreadSample(
                    sampleNanos, cpuNanos, info.getBlockedCount(), info.getWaitedCount());
            ThreadSample previous = previousThreads.put(threadId, current);
            if (!writeLog) {
                continue;
            }
            double cpuPercent = -1D;
            long blockedDelta = 0L;
            long waitedDelta = 0L;
            if (previous != null) {
                long elapsedNanos = Math.max(1L, current.sampleNanos() - previous.sampleNanos());
                if (current.cpuNanos() >= 0L && previous.cpuNanos() >= 0L) {
                    cpuPercent = percent(
                            Math.max(0L, current.cpuNanos() - previous.cpuNanos()), elapsedNanos);
                }
                blockedDelta = Math.max(0L, current.blockedCount() - previous.blockedCount());
                waitedDelta = Math.max(0L, current.waitedCount() - previous.waitedCount());
            }
            LoggerDef.SystemLogger.info(
                    "Scene platform thread load, thread={}, threadId={}, state={}, cpuPercent={}, blockedDelta={}, waitedDelta={}",
                    info.getThreadName(), threadId, info.getThreadState(), cpuPercent,
                    blockedDelta, waitedDelta);
        }
        previousThreads.keySet().removeIf(threadId -> !activeIds.contains(threadId));
    }

    private boolean enableThreadCpuTime() {
        if (!threadBean.isThreadCpuTimeSupported()) {
            return false;
        }
        try {
            if (!threadBean.isThreadCpuTimeEnabled()) {
                threadBean.setThreadCpuTimeEnabled(true);
            }
            return threadBean.isThreadCpuTimeEnabled();
        } catch (RuntimeException error) {
            LoggerDef.SystemLogger.warn("Cannot enable JVM thread CPU time, only state counters will be logged", error);
            return false;
        }
    }

    private void safeLogNow() {
        try {
            logNow();
        } catch (Throwable error) {
            LoggerDef.SystemLogger.error("Scene load logger failed", error);
        }
    }

    private static boolean isManagedThread(String threadName) {
        return threadName.startsWith(TICK_THREAD_PREFIX)
                || threadName.startsWith(PATH_THREAD_PREFIX)
                || threadName.startsWith(MIGRATION_THREAD_PREFIX);
    }

    private static String shardKey(SceneShardLoadSnapshot snapshot) {
        return snapshot.sceneId() + '#' + snapshot.shardIndex();
    }

    private static double percent(long numerator, long denominator) {
        return denominator <= 0L ? 0D : round2(numerator * 100D / denominator);
    }

    private static double round2(double value) {
        return Math.round(value * 100D) / 100D;
    }

    private record ThreadSample(
            long sampleNanos,
            long cpuNanos,
            long blockedCount,
            long waitedCount) {
    }
}
