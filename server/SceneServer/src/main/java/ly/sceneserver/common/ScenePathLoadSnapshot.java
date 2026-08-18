package ly.sceneserver.common;

/** 寻路平台线程池的只读负载快照；累计值供周期日志计算增量，峰值读取后重置。 */
public record ScenePathLoadSnapshot(
        long sampleNanoTime,
        int configuredThreads,
        int poolSize,
        int activeThreads,
        int largestPoolSize,
        int queuedTasks,
        long submittedTasks,
        long finishedTasks,
        long failedTasks,
        long rejectedTasks,
        long totalTaskNanos,
        long intervalMaxTaskNanos) {
}
