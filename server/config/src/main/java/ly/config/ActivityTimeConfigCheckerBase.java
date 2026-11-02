package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityTimeConfigCheckerBase extends AbstractConfigChecker<ActivityTimeConfig> {
  @Override
  public String getConfigFileName() {
    return "activityTime.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "type", "int"),
        new ConfigColumnMeta(3, "time", "string"),
        new ConfigColumnMeta(4, "timeShow", "string"),
        new ConfigColumnMeta(5, "week", "string"));
  }
}
