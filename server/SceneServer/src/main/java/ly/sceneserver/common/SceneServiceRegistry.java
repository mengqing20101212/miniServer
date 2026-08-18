package ly.sceneserver.common;

import ly.proto.Scene;

/** 公共 Handler 使用的场景服务注册表，避免本服和跨服 Handler 重复注册同一 CMD。 */
public final class SceneServiceRegistry {
    private final SceneRuntime runtime;
    private final SceneModeService localService;
    private final SceneModeService crossService;

    public SceneServiceRegistry(SceneRuntime runtime, SceneModeService localService, SceneModeService crossService) {
        this.runtime = runtime;
        this.localService = localService;
        this.crossService = crossService;
    }

    public SceneRuntime.SceneInstance resolve(String sceneId, Scene.SceneScope scope) {
        return service(scope).resolve(runtime, sceneId);
    }

    public SceneModeService service(Scene.SceneScope scope) {
        return scope == Scene.SceneScope.SCENE_SCOPE_CROSS ? crossService : localService;
    }

    public SceneRuntime runtime() {
        return runtime;
    }
}
