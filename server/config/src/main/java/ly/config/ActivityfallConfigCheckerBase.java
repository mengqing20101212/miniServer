package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityfallConfigCheckerBase extends AbstractConfigChecker<ActivityfallConfig> {
  @Override
  public String getConfigFileName() {
    return "activityfall.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "questType", "int"),
        new ConfigColumnMeta(2, "beizhu", "string"),
        new ConfigColumnMeta(3, "fallitem", "string"),
        new ConfigColumnMeta(4, "item", "string"),
        new ConfigColumnMeta(5, "turn", "int"),
        new ConfigColumnMeta(6, "title", "string"));
  }
}
