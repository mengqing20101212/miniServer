package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class StrategyRecommendLineupConfigCheckerBase extends AbstractConfigChecker<StrategyRecommendLineupConfig> {
  @Override
  public String getConfigFileName() {
    return "strategyRecommendLineup.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "heroIds", "STRING"),
        new ConfigColumnMeta(2, "sceneId", "INT"),
        new ConfigColumnMeta(3, "title", "STRING"),
        new ConfigColumnMeta(4, "actorDetail", "STRING"),
        new ConfigColumnMeta(5, "playDetail", "STRING"),
        new ConfigColumnMeta(6, "effectDetail", "STRING"));
  }
}
