package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ContractPoolConfigCheckerBase extends AbstractConfigChecker<ContractPoolConfig> {
  @Override
  public String getConfigFileName() {
    return "contractPool.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "oneDrawId", "int"),
        new ConfigColumnMeta(3, "tenDrawId", "int"),
        new ConfigColumnMeta(4, "chanceText", "string"),
        new ConfigColumnMeta(5, "topId", "int"));
  }
}
