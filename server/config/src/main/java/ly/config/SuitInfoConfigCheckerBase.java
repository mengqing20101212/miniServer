package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SuitInfoConfigCheckerBase extends AbstractConfigChecker<SuitInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "suitInfo.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "suitId", "int"),
        new ConfigColumnMeta(1, "suitName", "string"),
        new ConfigColumnMeta(2, "suitIcon", "int"),
        new ConfigColumnMeta(3, "suitIcon2", "int"),
        new ConfigColumnMeta(4, "suitActive", "string"),
        new ConfigColumnMeta(5, "twoSuitDec", "string"),
        new ConfigColumnMeta(6, "threeSuitDec", "string"));
  }
}
