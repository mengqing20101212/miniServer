package ly.sceneserver.local;

import ly.sceneserver.common.SceneModeService;
import ly.sceneserver.common.SceneScope;

/** 本服地图业务入口；公共 Scene Handler 通过该服务解析本服场景。 */
public final class LocalSceneService implements SceneModeService {
    private final String sceneId;

    public LocalSceneService() {
        this(System.getProperty("slg.scene.local-id", "world-1"));
    }

    public LocalSceneService(String sceneId) {
        this.sceneId = sceneId;
    }

    @Override
    public SceneScope scope() {
        return SceneScope.LOCAL;
    }

    @Override
    public String defaultSceneId() {
        return sceneId;
    }
}
