package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class StrategyRecommendConfigCheckerBase extends AbstractConfigChecker<StrategyRecommendConfig> {
  @Override
  public String getConfigFileName() {
    return "strategyRecommend.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "lineupGroupIds", "STRING"),
        new ConfigColumnMeta(2, "keySourceIds", "STRING"),
        new ConfigColumnMeta(3, "keyThinkingDetail", "STRING"),
        new ConfigColumnMeta(4, "recommendSourceIds", "STRING"),
        new ConfigColumnMeta(5, "heroPlayDetail", "STRING"),
        new ConfigColumnMeta(6, "skillDetail", "STRING"),
        new ConfigColumnMeta(7, "heroName", "STRING"));
  }
}
