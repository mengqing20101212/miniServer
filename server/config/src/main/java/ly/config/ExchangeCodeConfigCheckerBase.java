package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ExchangeCodeConfigCheckerBase extends AbstractConfigChecker<ExchangeCodeConfig> {
  @Override
  public String getConfigFileName() {
    return "exchangeCode.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "groupId", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "beginTime", "STRING"),
        new ConfigColumnMeta(3, "endTime", "STRING"),
        new ConfigColumnMeta(4, "codeNum", "INT"),
        new ConfigColumnMeta(5, "channel", "STRING"),
        new ConfigColumnMeta(6, "rewards", "STRING"),
        new ConfigColumnMeta(7, "limit1", "INT"),
        new ConfigColumnMeta(8, "limit2", "INT"),
        new ConfigColumnMeta(9, "limit3", "STRING"));
  }
}
