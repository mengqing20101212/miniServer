package ly.sceneserver.cross;

import ly.sceneserver.common.SceneModeService;
import ly.sceneserver.common.SceneScope;

/** 跨服地图业务入口；公共 Scene Handler 通过该服务解析跨服场景。 */
public final class CrossSceneService implements SceneModeService {
    private final String sceneId;

    public CrossSceneService() {
        this(System.getProperty("slg.scene.cross-id", "cross-1"));
    }

    public CrossSceneService(String sceneId) {
        this.sceneId = sceneId;
    }

    @Override
    public SceneScope scope() {
        return SceneScope.CROSS;
    }

    @Override
    public String defaultSceneId() {
        return sceneId;
    }
}
