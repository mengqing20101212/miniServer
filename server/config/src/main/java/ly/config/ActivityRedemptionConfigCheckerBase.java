package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityRedemptionConfigCheckerBase extends AbstractConfigChecker<ActivityRedemptionConfig> {
  @Override
  public String getConfigFileName() {
    return "activityRedemption.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "questType", "INT"),
        new ConfigColumnMeta(3, "scheDuling", "INT"),
        new ConfigColumnMeta(4, "exchangeNum", "STRING"),
        new ConfigColumnMeta(5, "drop", "INT"),
        new ConfigColumnMeta(6, "dropShow", "INT"),
        new ConfigColumnMeta(7, "finishMax", "INT"),
        new ConfigColumnMeta(8, "reset", "INT"));
  }
}
