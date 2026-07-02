package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SceneConfigCheckerBase extends AbstractConfigChecker<SceneConfig> {
  @Override
  public String getConfigFileName() {
    return "scene.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "sceneConfig", "STRING"),
        new ConfigColumnMeta(2, "sceneConfigEnemy", "STRING"),
        new ConfigColumnMeta(3, "resname", "STRING"),
        new ConfigColumnMeta(4, "profile", "INT"),
        new ConfigColumnMeta(5, "tpye", "INT"),
        new ConfigColumnMeta(6, "fogConfig", "STRING"),
        new ConfigColumnMeta(7, "lightConfig", "STRING"),
        new ConfigColumnMeta(8, "functionPoint", "STRING"),
        new ConfigColumnMeta(9, "functionCameraConfig", "STRING"),
        new ConfigColumnMeta(10, "roleLight", "STRING"),
        new ConfigColumnMeta(11, "WinPos", "STRING"),
        new ConfigColumnMeta(12, "eftResid", "INT"),
        new ConfigColumnMeta(13, "eftResOffset", "STRING"),
        new ConfigColumnMeta(14, "SFX", "STRING"),
        new ConfigColumnMeta(15, "sceneConfigRotation", "INT"));
  }
}
