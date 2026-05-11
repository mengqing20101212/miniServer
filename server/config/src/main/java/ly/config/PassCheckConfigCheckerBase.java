package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PassCheckConfigCheckerBase extends AbstractConfigChecker<PassCheckConfig> {
  @Override
  public String getConfigFileName() {
    return "passCheck.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "level", "INT"),
        new ConfigColumnMeta(2, "cost", "INT"),
        new ConfigColumnMeta(3, "reward", "STRING"),
        new ConfigColumnMeta(4, "seniorReward", "STRING"),
        new ConfigColumnMeta(5, "key", "INT"),
        new ConfigColumnMeta(6, "levelPrice", "INT"));
  }
}
