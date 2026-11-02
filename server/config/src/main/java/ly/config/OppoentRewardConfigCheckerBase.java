package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class OppoentRewardConfigCheckerBase extends AbstractConfigChecker<OppoentRewardConfig> {
  @Override
  public String getConfigFileName() {
    return "oppoentReward.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "rank", "string"),
        new ConfigColumnMeta(2, "reward", "int"),
        new ConfigColumnMeta(3, "rewardShow", "int"));
  }
}
