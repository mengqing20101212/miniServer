package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityfallConfigCheckerBase extends AbstractConfigChecker<ActivityfallConfig> {
  @Override
  public String getConfigFileName() {
    return "activityfall.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "questType", "INT"),
        new ConfigColumnMeta(2, "beizhu", "STRING"),
        new ConfigColumnMeta(3, "fallitem", "STRING"),
        new ConfigColumnMeta(4, "item", "STRING"),
        new ConfigColumnMeta(5, "turn", "INT"),
        new ConfigColumnMeta(6, "title", "STRING"));
  }
}
