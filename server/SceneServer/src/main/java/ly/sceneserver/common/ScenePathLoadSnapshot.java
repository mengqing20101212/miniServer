package ly.sceneserver.common;

/** A* 虚拟线程与受控 CPU 工作区的只读负载快照；累计值供周期日志计算增量。 */
public record ScenePathLoadSnapshot(
        long sampleNanoTime,
        /** 同时执行 A* 的最大数量，也是百万格工作区数量。 */
        int maxParallelism,
        /** 已接纳且尚未结束的完整寻路流程，包括等待迷雾快照、工作区和结果 Tick。 */
        int pendingTasks,
        /** 当前已经进入 A* 服务的虚拟线程，包含等待工作区的任务。 */
        int liveVirtualThreads,
        /** 当前正在执行 A* 的任务数量。 */
        int activeSearches,
        /** 当前采样周期中的 A* 并行峰值。 */
        int intervalPeakActiveSearches,
        /** 当前挂起等待 CPU 工作区的虚拟线程数量。 */
        int waitingTasks,
        /** 允许的寻路请求总量上限（执行中 + 等待中）。 */
        int maxPendingTasks,
        long submittedTasks,
        long finishedTasks,
        long failedTasks,
        long rejectedTasks,
        long totalTaskNanos,
        long intervalMaxTaskNanos) {
}
