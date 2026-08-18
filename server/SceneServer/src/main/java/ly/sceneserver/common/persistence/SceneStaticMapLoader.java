package ly.sceneserver.common.persistence;

import ly.sceneserver.common.SceneConfig;
import ly.sceneserver.common.SceneStaticMap;

/** 启动期静态地图加载接口；正式实现可读取 config 策划表、二进制地图文件或对象存储。 */
@FunctionalInterface
public interface SceneStaticMapLoader {
    void load(SceneConfig config, SceneStaticMap map);
}
