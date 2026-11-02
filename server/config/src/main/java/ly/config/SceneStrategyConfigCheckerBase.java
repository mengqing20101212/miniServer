package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SceneStrategyConfigCheckerBase extends AbstractConfigChecker<SceneStrategyConfig> {
  @Override
  public String getConfigFileName() {
    return "sceneStrategy.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "beizhu", "string"),
        new ConfigColumnMeta(2, "npcId", "int"),
        new ConfigColumnMeta(3, "strategyDec", "string"),
        new ConfigColumnMeta(4, "heroId", "string"),
        new ConfigColumnMeta(5, "nextId", "int"),
        new ConfigColumnMeta(6, "lastId", "int"),
        new ConfigColumnMeta(7, "sceneGroup", "int"));
  }
}
