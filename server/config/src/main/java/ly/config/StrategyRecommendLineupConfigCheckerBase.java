package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class StrategyRecommendLineupConfigCheckerBase extends AbstractConfigChecker<StrategyRecommendLineupConfig> {
  @Override
  public String getConfigFileName() {
    return "strategyRecommendLineup.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "heroIds", "string"),
        new ConfigColumnMeta(2, "sceneId", "int"),
        new ConfigColumnMeta(3, "title", "string"),
        new ConfigColumnMeta(4, "actorDetail", "string"),
        new ConfigColumnMeta(5, "playDetail", "string"),
        new ConfigColumnMeta(6, "effectDetail", "string"));
  }
}
