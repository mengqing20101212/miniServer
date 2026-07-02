package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroAIWeightConfigCheckerBase extends AbstractConfigChecker<HeroAIWeightConfig> {
  @Override
  public String getConfigFileName() {
    return "heroAIWeight.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "heroId", "INT"),
        new ConfigColumnMeta(1, "modelId", "INT"),
        new ConfigColumnMeta(2, "name", "STRING"),
        new ConfigColumnMeta(3, "des", "STRING"),
        new ConfigColumnMeta(4, "CLASS", "INT"),
        new ConfigColumnMeta(5, "sequence", "INT"),
        new ConfigColumnMeta(6, "weight", "INT"));
  }
}
