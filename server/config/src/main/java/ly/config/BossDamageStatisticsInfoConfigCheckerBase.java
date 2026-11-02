package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class BossDamageStatisticsInfoConfigCheckerBase extends AbstractConfigChecker<BossDamageStatisticsInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "bossDamageStatisticsInfo.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "parameter1", "string"),
        new ConfigColumnMeta(3, "parameter2", "string"),
        new ConfigColumnMeta(4, "parameter3", "string"));
  }
}
