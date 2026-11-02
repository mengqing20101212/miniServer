package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityExploreConfigCheckerBase extends AbstractConfigChecker<ActivityExploreConfig> {
  @Override
  public String getConfigFileName() {
    return "activityExplore.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "scheDuling", "int"),
        new ConfigColumnMeta(2, "layers", "int"),
        new ConfigColumnMeta(3, "specialReward", "string"),
        new ConfigColumnMeta(4, "dropShowId", "int"),
        new ConfigColumnMeta(5, "normalReward", "string"),
        new ConfigColumnMeta(6, "exploreMap", "string"));
  }
}
