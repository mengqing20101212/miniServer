package ly.sceneserver.common.persistence;

import ly.sceneserver.common.SceneConfig;
import ly.sceneserver.common.SceneStaticMap;
import ly.sceneserver.common.SceneTerrainType;
import ly.sceneserver.common.SceneTileFlags;

/**
 * 策划地图接入前的安全默认加载器：所有格子初始化为可行走平原，而不是保留无法寻路的零值数组。
 */
public final class FlatSceneStaticMapLoader implements SceneStaticMapLoader {
    @Override
    public void load(SceneConfig config, SceneStaticMap map) {
        if (map.width() != config.width() || map.height() != config.height()) {
            throw new IllegalArgumentException("static map size does not match scene config");
        }
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                map.set(x, y, SceneTerrainType.PLAIN, 0, SceneTileFlags.WALKABLE, 0);
            }
        }
    }
}
