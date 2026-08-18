package ly.sceneserver.common;

/**
 * 一个逻辑场景的静态运行参数。
 *
 * <p>第一阶段默认支持 1000 x 1000 的地图，并在同一个 SceneServer JVM 内拆成多个
 * SceneShard。SceneShard 是逻辑执行分片，不是独立进程，也不改变玩家当前的分区分服路由。
 */
public record SceneConfig(
        String sceneId,
        int width,
        int height,
        int shardCount,
        int regionSize,
        int tickMillis) {

    public SceneConfig {
        if (sceneId == null || sceneId.isBlank()) {
            throw new IllegalArgumentException("sceneId must not be blank");
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("scene width and height must be positive");
        }
        if (width > Integer.MAX_VALUE / height) {
            throw new IllegalArgumentException("scene is too large");
        }
        if (shardCount <= 0 || regionSize <= 0 || tickMillis <= 0) {
            throw new IllegalArgumentException("shardCount, regionSize and tickMillis must be positive");
        }
    }

    /** 创建当前 SLG 第一阶段使用的标准地图配置。 */
    public static SceneConfig standard(String sceneId) {
        return new SceneConfig(sceneId, 1_000, 1_000, 4, 32, 100);
    }
}
