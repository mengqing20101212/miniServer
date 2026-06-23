package ly.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import ly.LoggerDef;

/**
 * JVM 级死锁检测器。
 *
 * <p>这里使用 JDK 自带的 {@link ThreadMXBean#findDeadlockedThreads()}，可以检测
 * {@code synchronized} 监视器锁和 {@code ReentrantLock} 这类 ownable synchronizer 死锁。
 * 检测到死锁后会把相关线程的锁信息和堆栈打到 system 日志，方便线上第一时间定位持锁代码。
 *
 * <p>注意：这个检测器只能发现 JVM 锁层面的死锁。业务层面的互相等待，例如两个玩家队列互相等 future，
 * 需要业务自己的等待图检测。
 */
public final class DeadlockDetector {
    private static final long DEFAULT_INTERVAL_MILLIS = 30_000L;
    private static final long DEFAULT_REPEAT_LOG_MILLIS = 300_000L;
    private static final String ENABLED_PROPERTY = "mini.deadlock.detector.enabled";
    private static final String INTERVAL_PROPERTY = "mini.deadlock.detector.intervalMillis";
    private static final String REPEAT_LOG_PROPERTY = "mini.deadlock.detector.repeatLogMillis";
    private static final String DUMP_ALL_ON_DEADLOCK_PROPERTY = "mini.deadlock.detector.dumpAllThreadsOnDeadlock";

    private static final AtomicBoolean STARTED = new AtomicBoolean();
    private static final ThreadMXBean THREAD_MX_BEAN = ManagementFactory.getThreadMXBean();

    private static volatile ScheduledExecutorService executor;
    private static volatile String lastDeadlockSignature = "";
    private static volatile long lastLogMillis;

    private DeadlockDetector() {
    }

    /**
     * 使用系统参数启动检测器。
     *
     * <p>可用参数：
     * <ul>
     *   <li>{@code -Dmini.deadlock.detector.enabled=false}：关闭检测。</li>
     *   <li>{@code -Dmini.deadlock.detector.intervalMillis=30000}：检测间隔。</li>
     *   <li>{@code -Dmini.deadlock.detector.repeatLogMillis=300000}：同一批死锁重复打印间隔。</li>
     *   <li>{@code -Dmini.deadlock.detector.dumpAllThreadsOnDeadlock=false}：死锁时不附带全线程 dump。</li>
     * </ul>
     */
    public static void start() {
        if (!Boolean.parseBoolean(System.getProperty(ENABLED_PROPERTY, "true"))) {
            LoggerDef.SystemLogger.info("JVM死锁检测器已关闭，property={}", ENABLED_PROPERTY);
            return;
        }
        start(readPositiveLong(INTERVAL_PROPERTY, DEFAULT_INTERVAL_MILLIS),
                readPositiveLong(REPEAT_LOG_PROPERTY, DEFAULT_REPEAT_LOG_MILLIS));
    }

    /** 启动检测器；重复调用不会创建多个检测线程。 */
    public static void start(long intervalMillis, long repeatLogMillis) {
        if (intervalMillis <= 0) {
            throw new IllegalArgumentException("intervalMillis must be positive");
        }
        if (repeatLogMillis <= 0) {
            throw new IllegalArgumentException("repeatLogMillis must be positive");
        }
        if (!STARTED.compareAndSet(false, true)) {
            return;
        }

        executor = Executors.newSingleThreadScheduledExecutor(
                runnable -> {
                    Thread thread = new Thread(runnable, "jvm-deadlock-detector");
                    thread.setDaemon(true);
                    return thread;
                });
        executor.scheduleWithFixedDelay(
                () -> safeCheckNow(repeatLogMillis),
                intervalMillis,
                intervalMillis,
                TimeUnit.MILLISECONDS);
        LoggerDef.SystemLogger.info(
                "JVM死锁检测器启动，intervalMillis={}, repeatLogMillis={}",
                intervalMillis,
                repeatLogMillis);
    }

    /** 停止检测器，主要用于单元测试或独立工具进程退出前清理。 */
    public static void stop() {
        ScheduledExecutorService current = executor;
        executor = null;
        STARTED.set(false);
        if (current != null) {
            current.shutdownNow();
        }
        lastDeadlockSignature = "";
        lastLogMillis = 0L;
    }

    /**
     * 立即检查一次死锁。
     *
     * @return 当前检测到的死锁线程 id；没有死锁时返回空数组
     */
    public static long[] checkNow() {
        long[] threadIds = THREAD_MX_BEAN.findDeadlockedThreads();
        return threadIds == null ? new long[0] : threadIds;
    }

    /**
     * dump 当前 JVM 所有线程堆栈。
     *
     * <p>这个方法不判断死锁，只负责拿一份当前线程快照。线上排查卡顿、线程池耗尽、
     * 玩家虚拟线程长时间等待时，可以手动调用这个方法或 {@link #logAllThreads(String)}。
     */
    public static String dumpAllThreads() {
        ThreadInfo[] threadInfos = THREAD_MX_BEAN.dumpAllThreads(true, true);
        if (threadInfos == null || threadInfos.length == 0) {
            return "未获取到线程堆栈";
        }
        Arrays.sort(threadInfos, Comparator.comparingLong(ThreadInfo::getThreadId));
        StringBuilder builder = new StringBuilder(Math.max(4096, threadInfos.length * 1024));
        builder.append("JVM线程总数=").append(threadInfos.length).append('\n');
        for (ThreadInfo info : threadInfos) {
            appendThreadInfo(builder, info);
        }
        return builder.toString();
    }

    /** 立即把当前 JVM 所有线程堆栈打到 system error 日志。 */
    public static void logAllThreads(String reason) {
        LoggerDef.SystemLogger.error("JVM线程堆栈dump，reason={}\n{}", reason, dumpAllThreads());
    }

    private static void safeCheckNow(long repeatLogMillis) {
        try {
            long[] threadIds = checkNow();
            if (threadIds.length == 0) {
                lastDeadlockSignature = "";
                lastLogMillis = 0L;
                return;
            }

            Arrays.sort(threadIds);
            String signature = Arrays.toString(threadIds);
            long now = System.currentTimeMillis();
            if (Objects.equals(signature, lastDeadlockSignature)
                    && now - lastLogMillis < repeatLogMillis) {
                return;
            }

            lastDeadlockSignature = signature;
            lastLogMillis = now;
            ThreadInfo[] threadInfos = THREAD_MX_BEAN.getThreadInfo(threadIds, true, true);
            LoggerDef.SystemLogger.error("检测到JVM线程死锁，threadIds={}\n{}", signature, buildReport(threadInfos));
            if (Boolean.parseBoolean(System.getProperty(DUMP_ALL_ON_DEADLOCK_PROPERTY, "true"))) {
                logAllThreads("deadlock:" + signature);
            }
        } catch (Throwable e) {
            LoggerDef.SystemLogger.error("JVM死锁检测器执行失败", e);
        }
    }

    static String buildReport(ThreadInfo[] threadInfos) {
        StringBuilder builder = new StringBuilder(4096);
        if (threadInfos == null || threadInfos.length == 0) {
            return "未获取到死锁线程详情";
        }
        for (ThreadInfo info : threadInfos) {
            if (info == null) {
                continue;
            }
            appendThreadInfo(builder, info);
        }
        return builder.toString();
    }

    private static void appendThreadInfo(StringBuilder builder, ThreadInfo info) {
        builder.append('"')
                .append(info.getThreadName())
                .append("\" id=")
                .append(info.getThreadId())
                .append(" state=")
                .append(info.getThreadState())
                .append('\n');
        builder.append("  waitingLock=").append(info.getLockInfo()).append('\n');
        builder.append("  lockOwner=").append(info.getLockOwnerName())
                .append(" id=").append(info.getLockOwnerId()).append('\n');
        Arrays.stream(info.getLockedMonitors())
                .forEach(monitor -> builder.append("  lockedMonitor=").append(monitor).append('\n'));
        Arrays.stream(info.getLockedSynchronizers())
                .forEach(lock -> builder.append("  lockedSynchronizer=").append(lock).append('\n'));
        for (StackTraceElement element : info.getStackTrace()) {
            builder.append("    at ").append(element).append('\n');
        }
        builder.append('\n');
    }

    private static long readPositiveLong(String propertyName, long defaultValue) {
        String value = System.getProperty(propertyName);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            long parsed = Long.parseLong(value.trim());
            if (parsed > 0) {
                return parsed;
            }
        } catch (NumberFormatException e) {
            LoggerDef.SystemLogger.warn("JVM死锁检测器参数非法，property={}, value={}", propertyName, value);
        }
        return defaultValue;
    }
}
