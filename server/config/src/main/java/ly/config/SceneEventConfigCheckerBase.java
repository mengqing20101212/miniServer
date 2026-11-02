package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SceneEventConfigCheckerBase extends AbstractConfigChecker<SceneEventConfig> {
  @Override
  public String getConfigFileName() {
    return "sceneEvent.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "description", "string"),
        new ConfigColumnMeta(2, "triggerTypeList", "string"),
        new ConfigColumnMeta(3, "treeName", "string"),
        new ConfigColumnMeta(4, "uitype", "int"),
        new ConfigColumnMeta(5, "param_1", "string"),
        new ConfigColumnMeta(6, "param_2", "string"),
        new ConfigColumnMeta(7, "param_3", "string"),
        new ConfigColumnMeta(8, "param_4", "string"),
        new ConfigColumnMeta(9, "loopNum", "int"));
  }
}
