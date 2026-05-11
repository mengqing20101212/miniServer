package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitycashGiftConfigCheckerBase extends AbstractConfigChecker<ActivitycashGiftConfig> {
  @Override
  public String getConfigFileName() {
    return "activitycashGift.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "RecommendedPackage", "STRING"),
        new ConfigColumnMeta(2, "GradeInterval", "STRING"),
        new ConfigColumnMeta(3, "IsTopGift", "INT"));
  }
}
