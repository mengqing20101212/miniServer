package ly.sceneserver.common;

/**
 * SceneShard 的只读负载快照。
 *
 * <p>累计值用于计算两个采样点之间的平均负载，峰值字段只表示本次日志周期，读取后会重置。
 */
public record SceneShardLoadSnapshot(
        String sceneId,
        int shardIndex,
        int tickMillis,
        long sampleNanoTime,
        long tickNumber,
        long lastTickCompletedMillis,
        long totalTickNanos,
        long lastTickNanos,
        long intervalMaxTickNanos,
        long totalCommands,
        long slowTickCount,
        long failedTickCount,
        int queuedCommands,
        int intervalPeakQueuedCommands,
        int objectCount,
        int activeViewerCount,
        int fogPlayerCount,
        String lastThreadName) {
}
