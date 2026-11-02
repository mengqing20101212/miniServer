package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityRedemptionConfigCheckerBase extends AbstractConfigChecker<ActivityRedemptionConfig> {
  @Override
  public String getConfigFileName() {
    return "activityRedemption.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "questType", "int"),
        new ConfigColumnMeta(3, "scheDuling", "int"),
        new ConfigColumnMeta(4, "exchangeNum", "string"),
        new ConfigColumnMeta(5, "drop", "int"),
        new ConfigColumnMeta(6, "dropShow", "int"),
        new ConfigColumnMeta(7, "finishMax", "int"),
        new ConfigColumnMeta(8, "reset", "int"));
  }
}
