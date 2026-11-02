package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SocietyCleanCityConfigCheckerBase extends AbstractConfigChecker<SocietyCleanCityConfig> {
  @Override
  public String getConfigFileName() {
    return "societyCleanCity.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "cell", "string"),
        new ConfigColumnMeta(2, "progressdrop25", "int"),
        new ConfigColumnMeta(3, "progressdrop50", "int"),
        new ConfigColumnMeta(4, "progressdrop75", "int"),
        new ConfigColumnMeta(5, "progressdrop100", "int"),
        new ConfigColumnMeta(6, "rewardEvent", "string"),
        new ConfigColumnMeta(7, "giveEvent", "string"),
        new ConfigColumnMeta(8, "greatReward", "string"),
        new ConfigColumnMeta(9, "repairEvent", "string"),
        new ConfigColumnMeta(10, "storyEvent", "string"),
        new ConfigColumnMeta(11, "trapEvent", "string"),
        new ConfigColumnMeta(12, "bossEvent", "string"));
  }
}
