package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroAIWeightConfigCheckerBase extends AbstractConfigChecker<HeroAIWeightConfig> {
  @Override
  public String getConfigFileName() {
    return "heroAIWeight.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "heroId", "int"),
        new ConfigColumnMeta(1, "modelId", "int"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "des", "string"),
        new ConfigColumnMeta(4, "CLASS", "int"),
        new ConfigColumnMeta(5, "sequence", "int"),
        new ConfigColumnMeta(6, "weight", "int"));
  }
}
