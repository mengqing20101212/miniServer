package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ManualIntegralRewardConfigCheckerBase extends AbstractConfigChecker<ManualIntegralRewardConfig> {
  @Override
  public String getConfigFileName() {
    return "manualIntegralReward.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "group", "int"),
        new ConfigColumnMeta(2, "integral", "int"),
        new ConfigColumnMeta(3, "rewardShow", "string"),
        new ConfigColumnMeta(4, "drop", "int"));
  }
}
