package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CircuitRecommendConfigCheckerBase extends AbstractConfigChecker<CircuitRecommendConfig> {
  @Override
  public String getConfigFileName() {
    return "circuitRecommend.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "recommend1", "string"),
        new ConfigColumnMeta(3, "recommend2", "string"),
        new ConfigColumnMeta(4, "recommendAttr1", "string"),
        new ConfigColumnMeta(5, "recommendAttr2", "string"),
        new ConfigColumnMeta(6, "recommendAttr3", "string"),
        new ConfigColumnMeta(7, "recommendWord", "string"));
  }
}
