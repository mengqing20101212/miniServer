package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ScenePointConfigCheckerBase extends AbstractConfigChecker<ScenePointConfig> {
  @Override
  public String getConfigFileName() {
    return "scenePoint.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "sceneId", "int"),
        new ConfigColumnMeta(2, "scenePointList", "string"),
        new ConfigColumnMeta(3, "pointRoleList", "string"),
        new ConfigColumnMeta(4, "functionCameraConfig", "string"),
        new ConfigColumnMeta(5, "freeScenePointList", "string"),
        new ConfigColumnMeta(6, "freeCameraConfig", "string"),
        new ConfigColumnMeta(7, "Param_1", "string"),
        new ConfigColumnMeta(8, "groupScenePointList", "string"),
        new ConfigColumnMeta(9, "groupCameraConfig", "string"));
  }
}
