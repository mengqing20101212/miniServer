package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SocietyCleanCityConfigCheckerBase extends AbstractConfigChecker<SocietyCleanCityConfig> {
  @Override
  public String getConfigFileName() {
    return "societyCleanCity.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "cell", "STRING"),
        new ConfigColumnMeta(2, "progressdrop25", "INT"),
        new ConfigColumnMeta(3, "progressdrop50", "INT"),
        new ConfigColumnMeta(4, "progressdrop75", "INT"),
        new ConfigColumnMeta(5, "progressdrop100", "INT"),
        new ConfigColumnMeta(6, "rewardEvent", "STRING"),
        new ConfigColumnMeta(7, "giveEvent", "STRING"),
        new ConfigColumnMeta(8, "greatReward", "STRING"),
        new ConfigColumnMeta(9, "repairEvent", "STRING"),
        new ConfigColumnMeta(10, "storyEvent", "STRING"),
        new ConfigColumnMeta(11, "trapEvent", "STRING"),
        new ConfigColumnMeta(12, "bossEvent", "STRING"));
  }
}
