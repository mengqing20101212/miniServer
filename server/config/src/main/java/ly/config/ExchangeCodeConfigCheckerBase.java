package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ExchangeCodeConfigCheckerBase extends AbstractConfigChecker<ExchangeCodeConfig> {
  @Override
  public String getConfigFileName() {
    return "exchangeCode.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "groupId", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "beginTime", "string"),
        new ConfigColumnMeta(3, "endTime", "string"),
        new ConfigColumnMeta(4, "codeNum", "int"),
        new ConfigColumnMeta(5, "channel", "string"),
        new ConfigColumnMeta(6, "rewards", "string"),
        new ConfigColumnMeta(7, "limit1", "int"),
        new ConfigColumnMeta(8, "limit2", "int"),
        new ConfigColumnMeta(9, "limit3", "string"));
  }
}
