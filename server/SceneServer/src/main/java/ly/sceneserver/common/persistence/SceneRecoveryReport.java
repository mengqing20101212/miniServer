package ly.sceneserver.common.persistence;

/** SceneServer 启动恢复结果，用于启动日志和自动化验收。 */
public record SceneRecoveryReport(
        int sceneCount,
        long staticCellCount,
        long restoredPlayerCount,
        long restoredCityCount,
        long restoredFogBlockCount,
        long restoredSceneObjectCount,
        long restoredMarchCount,
        long restoredRallyCount,
        long costMillis) {

    /** 兼容只恢复玩家场景投影的测试或自定义启动器。 */
    public SceneRecoveryReport(
            int sceneCount,
            long staticCellCount,
            long restoredPlayerCount,
            long restoredCityCount,
            long restoredFogBlockCount,
            long costMillis) {
        this(sceneCount, staticCellCount, restoredPlayerCount, restoredCityCount,
                restoredFogBlockCount, 0L, 0L, 0L, costMillis);
    }
}
