package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SceneTimeConfigCheckerBase extends AbstractConfigChecker<SceneTimeConfig> {
  @Override
  public String getConfigFileName() {
    return "sceneTime.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "des", "STRING"),
        new ConfigColumnMeta(2, "reConnectTime", "INT"),
        new ConfigColumnMeta(3, "offLineTime", "INT"),
        new ConfigColumnMeta(4, "turnTime", "INT"),
        new ConfigColumnMeta(5, "playerLoadingTime", "INT"),
        new ConfigColumnMeta(6, "reConnectTimeMult", "INT"),
        new ConfigColumnMeta(7, "offLineTimeMult", "INT"),
        new ConfigColumnMeta(8, "turnTimeMult", "INT"),
        new ConfigColumnMeta(9, "playerLoadingTimeMult", "INT"));
  }
}
