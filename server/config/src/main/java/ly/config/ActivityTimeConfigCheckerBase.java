package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityTimeConfigCheckerBase extends AbstractConfigChecker<ActivityTimeConfig> {
  @Override
  public String getConfigFileName() {
    return "activityTime.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "type", "INT"),
        new ConfigColumnMeta(3, "time", "STRING"),
        new ConfigColumnMeta(4, "timeShow", "STRING"),
        new ConfigColumnMeta(5, "week", "STRING"));
  }
}
