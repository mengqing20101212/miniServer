package ly.sceneserver.common;

/** 专用热点 Region 迁移线程的累计和当前负载快照。 */
public record SceneRegionMigrationLoadSnapshot(
        /** 当前已经创建的平台线程数；迁移线程采用懒启动。 */
        int poolSize,
        /** 当前正在执行迁移编排的线程数，只可能是 0 或 1。 */
        int activeThreads,
        /** 等待执行的迁移数量；持续大于 0 表示均衡策略过于激进。 */
        int queuedTasks,
        /** 提交总数，包含后来被队列拒绝的任务。 */
        long submittedTasks,
        /** 真正进入迁移线程并结束的任务总数。 */
        long finishedTasks,
        /** 成功完成 owner 切换和目标 FIFO 屏障的任务总数。 */
        long succeededTasks,
        /** 导出、校验、安装或回滚阶段失败的任务总数。 */
        long failedTasks,
        /** 因迁移队列已满或服务关闭而拒绝的任务总数。 */
        long rejectedTasks,
        /** 所有已结束迁移在专用线程中占用的累计墙钟时间。 */
        long totalTaskNanos,
        /** 当前日志窗口内单次迁移最大耗时，读取后重置。 */
        long intervalMaxTaskNanos,
        /** 创建快照时的单调时钟，用于计算两个采样点之间的速率。 */
        long sampleNanoTime) {
}
