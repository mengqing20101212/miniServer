package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityshareConfigCheckerBase extends AbstractConfigChecker<ActivityshareConfig> {
  @Override
  public String getConfigFileName() {
    return "activityshare.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "beizhu", "string"),
        new ConfigColumnMeta(2, "share_type", "int"),
        new ConfigColumnMeta(3, "drop", "int"),
        new ConfigColumnMeta(4, "dropShow", "int"),
        new ConfigColumnMeta(5, "name", "string"));
  }
}
