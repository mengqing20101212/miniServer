package ly.sceneserver.common;

/** 本服和跨服场景都实现的最小业务边界。 */
public interface SceneModeService {
    SceneScope scope();

    String defaultSceneId();

    /**
     * 当前业务模式允许访问的场景 id。
     *
     * <p>第一阶段每种模式只有一个默认场景，因此使用精确匹配；后续一个模式承载多个
     * 活动场景时，可以由具体实现覆盖这个方法，不需要修改公共 Handler。
     */
    default boolean accepts(String sceneId) {
        return defaultSceneId().equals(sceneId);
    }

    default SceneRuntime.SceneInstance resolve(SceneRuntime runtime, String sceneId) {
        String actualSceneId = sceneId == null || sceneId.isBlank() ? defaultSceneId() : sceneId;
        if (!accepts(actualSceneId)) {
            throw new IllegalArgumentException("scene mode does not accept scene: " + actualSceneId);
        }
        SceneRuntime.SceneInstance scene = runtime.scene(actualSceneId);
        return scene;
    }
}
