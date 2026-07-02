package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class BossDamageStatisticsInfoConfigCheckerBase extends AbstractConfigChecker<BossDamageStatisticsInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "bossDamageStatisticsInfo.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "parameter1", "STRING"),
        new ConfigColumnMeta(3, "parameter2", "STRING"),
        new ConfigColumnMeta(4, "parameter3", "STRING"));
  }
}
