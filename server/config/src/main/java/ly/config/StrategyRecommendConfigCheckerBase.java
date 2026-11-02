package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class StrategyRecommendConfigCheckerBase extends AbstractConfigChecker<StrategyRecommendConfig> {
  @Override
  public String getConfigFileName() {
    return "strategyRecommend.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "lineupGroupIds", "string"),
        new ConfigColumnMeta(2, "keySourceIds", "string"),
        new ConfigColumnMeta(3, "keyThinkingDetail", "string"),
        new ConfigColumnMeta(4, "recommendSourceIds", "string"),
        new ConfigColumnMeta(5, "heroPlayDetail", "string"),
        new ConfigColumnMeta(6, "skillDetail", "string"),
        new ConfigColumnMeta(7, "heroName", "string"));
  }
}
