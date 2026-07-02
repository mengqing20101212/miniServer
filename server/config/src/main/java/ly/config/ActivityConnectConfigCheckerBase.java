package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityConnectConfigCheckerBase extends AbstractConfigChecker<ActivityConnectConfig> {
  @Override
  public String getConfigFileName() {
    return "activityConnect.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "stage", "INT"),
        new ConfigColumnMeta(2, "drop", "INT"),
        new ConfigColumnMeta(3, "dropShow", "INT"),
        new ConfigColumnMeta(4, "position", "STRING"));
  }
}
