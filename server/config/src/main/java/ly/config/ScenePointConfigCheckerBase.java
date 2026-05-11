package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ScenePointConfigCheckerBase extends AbstractConfigChecker<ScenePointConfig> {
  @Override
  public String getConfigFileName() {
    return "scenePoint.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "sceneId", "INT"),
        new ConfigColumnMeta(2, "scenePointList", "STRING"),
        new ConfigColumnMeta(3, "pointRoleList", "STRING"),
        new ConfigColumnMeta(4, "functionCameraConfig", "STRING"),
        new ConfigColumnMeta(5, "freeScenePointList", "STRING"),
        new ConfigColumnMeta(6, "freeCameraConfig", "STRING"),
        new ConfigColumnMeta(7, "Param_1", "STRING"),
        new ConfigColumnMeta(8, "groupScenePointList", "STRING"),
        new ConfigColumnMeta(9, "groupCameraConfig", "STRING"));
  }
}
