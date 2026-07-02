package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ResRecoveryConfigCheckerBase extends AbstractConfigChecker<ResRecoveryConfig> {
  @Override
  public String getConfigFileName() {
    return "resRecovery.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "progress", "INT"),
        new ConfigColumnMeta(3, "eachReward", "STRING"),
        new ConfigColumnMeta(4, "attenuationRate", "INT"));
  }
}
