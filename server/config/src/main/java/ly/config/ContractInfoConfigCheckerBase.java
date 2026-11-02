package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ContractInfoConfigCheckerBase extends AbstractConfigChecker<ContractInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "contractInfo.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "welfareId", "int"),
        new ConfigColumnMeta(2, "contractType", "int"),
        new ConfigColumnMeta(3, "item", "int"),
        new ConfigColumnMeta(4, "num", "int"),
        new ConfigColumnMeta(5, "contractNum", "int"),
        new ConfigColumnMeta(6, "dropId", "int"),
        new ConfigColumnMeta(7, "sumAwardId", "string"),
        new ConfigColumnMeta(8, "desc", "string"));
  }
}
