package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SuitInfoConfigCheckerBase extends AbstractConfigChecker<SuitInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "suitInfo.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "suitId", "INT"),
        new ConfigColumnMeta(1, "suitName", "STRING"),
        new ConfigColumnMeta(2, "suitIcon", "INT"),
        new ConfigColumnMeta(3, "suitIcon2", "INT"),
        new ConfigColumnMeta(4, "suitActive", "STRING"),
        new ConfigColumnMeta(5, "twoSuitDec", "STRING"),
        new ConfigColumnMeta(6, "threeSuitDec", "STRING"));
  }
}
