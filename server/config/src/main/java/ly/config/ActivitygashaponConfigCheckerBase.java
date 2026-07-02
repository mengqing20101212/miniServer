package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitygashaponConfigCheckerBase extends AbstractConfigChecker<ActivitygashaponConfig> {
  @Override
  public String getConfigFileName() {
    return "activitygashapon.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "scheDuling", "INT"),
        new ConfigColumnMeta(2, "itemId", "INT"),
        new ConfigColumnMeta(3, "count", "INT"),
        new ConfigColumnMeta(4, "gashaponWeights", "STRING"),
        new ConfigColumnMeta(5, "gachaBox", "STRING"),
        new ConfigColumnMeta(6, "RewardShow", "INT"));
  }
}
