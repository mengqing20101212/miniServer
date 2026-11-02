package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SceneTimeConfigCheckerBase extends AbstractConfigChecker<SceneTimeConfig> {
  @Override
  public String getConfigFileName() {
    return "sceneTime.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "des", "string"),
        new ConfigColumnMeta(2, "reConnectTime", "int"),
        new ConfigColumnMeta(3, "offLineTime", "int"),
        new ConfigColumnMeta(4, "turnTime", "int"),
        new ConfigColumnMeta(5, "playerLoadingTime", "int"),
        new ConfigColumnMeta(6, "reConnectTimeMult", "int"),
        new ConfigColumnMeta(7, "offLineTimeMult", "int"),
        new ConfigColumnMeta(8, "turnTimeMult", "int"),
        new ConfigColumnMeta(9, "playerLoadingTimeMult", "int"));
  }
}
