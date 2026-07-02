package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ContractInfoConfigCheckerBase extends AbstractConfigChecker<ContractInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "contractInfo.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "welfareId", "INT"),
        new ConfigColumnMeta(2, "contractType", "INT"),
        new ConfigColumnMeta(3, "item", "INT"),
        new ConfigColumnMeta(4, "num", "INT"),
        new ConfigColumnMeta(5, "contractNum", "INT"),
        new ConfigColumnMeta(6, "dropId", "INT"),
        new ConfigColumnMeta(7, "sumAwardId", "STRING"),
        new ConfigColumnMeta(8, "desc", "STRING"));
  }
}
