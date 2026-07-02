package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityExploreConfigCheckerBase extends AbstractConfigChecker<ActivityExploreConfig> {
  @Override
  public String getConfigFileName() {
    return "activityExplore.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "scheDuling", "INT"),
        new ConfigColumnMeta(2, "layers", "INT"),
        new ConfigColumnMeta(3, "specialReward", "STRING"),
        new ConfigColumnMeta(4, "dropShowId", "INT"),
        new ConfigColumnMeta(5, "normalReward", "STRING"),
        new ConfigColumnMeta(6, "exploreMap", "STRING"));
  }
}
