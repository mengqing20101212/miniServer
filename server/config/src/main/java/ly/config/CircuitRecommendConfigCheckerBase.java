package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CircuitRecommendConfigCheckerBase extends AbstractConfigChecker<CircuitRecommendConfig> {
  @Override
  public String getConfigFileName() {
    return "circuitRecommend.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "recommend1", "STRING"),
        new ConfigColumnMeta(3, "recommend2", "STRING"),
        new ConfigColumnMeta(4, "recommendAttr1", "STRING"),
        new ConfigColumnMeta(5, "recommendAttr2", "STRING"),
        new ConfigColumnMeta(6, "recommendAttr3", "STRING"),
        new ConfigColumnMeta(7, "recommendWord", "STRING"));
  }
}
