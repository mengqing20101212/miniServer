package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class PassCheckConfigCheckerBase extends AbstractConfigChecker<PassCheckConfig> {
  @Override
  public String getConfigFileName() {
    return "passCheck.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "level", "int"),
        new ConfigColumnMeta(2, "cost", "int"),
        new ConfigColumnMeta(3, "reward", "string"),
        new ConfigColumnMeta(4, "seniorReward", "string"),
        new ConfigColumnMeta(5, "key", "int"),
        new ConfigColumnMeta(6, "levelPrice", "int"));
  }
}
