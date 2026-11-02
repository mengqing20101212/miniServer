package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitygashaponConfigCheckerBase extends AbstractConfigChecker<ActivitygashaponConfig> {
  @Override
  public String getConfigFileName() {
    return "activitygashapon.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "scheDuling", "int"),
        new ConfigColumnMeta(2, "itemId", "int"),
        new ConfigColumnMeta(3, "count", "int"),
        new ConfigColumnMeta(4, "gashaponWeights", "string"),
        new ConfigColumnMeta(5, "gachaBox", "string"),
        new ConfigColumnMeta(6, "RewardShow", "int"));
  }
}
