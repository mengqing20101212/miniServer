package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SceneStrategyConfigCheckerBase extends AbstractConfigChecker<SceneStrategyConfig> {
  @Override
  public String getConfigFileName() {
    return "sceneStrategy.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "beizhu", "STRING"),
        new ConfigColumnMeta(2, "npcId", "INT"),
        new ConfigColumnMeta(3, "strategyDec", "STRING"),
        new ConfigColumnMeta(4, "heroId", "STRING"),
        new ConfigColumnMeta(5, "nextId", "INT"),
        new ConfigColumnMeta(6, "lastId", "INT"),
        new ConfigColumnMeta(7, "sceneGroup", "INT"));
  }
}
