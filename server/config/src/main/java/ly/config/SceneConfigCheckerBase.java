package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SceneConfigCheckerBase extends AbstractConfigChecker<SceneConfig> {
  @Override
  public String getConfigFileName() {
    return "scene.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "sceneConfig", "string"),
        new ConfigColumnMeta(2, "sceneConfigEnemy", "string"),
        new ConfigColumnMeta(3, "resname", "string"),
        new ConfigColumnMeta(4, "profile", "int"),
        new ConfigColumnMeta(5, "tpye", "int"),
        new ConfigColumnMeta(6, "fogConfig", "string"),
        new ConfigColumnMeta(7, "lightConfig", "string"),
        new ConfigColumnMeta(8, "functionPoint", "string"),
        new ConfigColumnMeta(9, "functionCameraConfig", "string"),
        new ConfigColumnMeta(10, "roleLight", "string"),
        new ConfigColumnMeta(11, "WinPos", "string"),
        new ConfigColumnMeta(12, "eftResid", "int"),
        new ConfigColumnMeta(13, "eftResOffset", "string"),
        new ConfigColumnMeta(14, "SFX", "string"),
        new ConfigColumnMeta(15, "sceneConfigRotation", "int"));
  }
}
