package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SceneEventConfigCheckerBase extends AbstractConfigChecker<SceneEventConfig> {
  @Override
  public String getConfigFileName() {
    return "sceneEvent.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "description", "STRING"),
        new ConfigColumnMeta(2, "triggerTypeList", "STRING"),
        new ConfigColumnMeta(3, "treeName", "STRING"),
        new ConfigColumnMeta(4, "uitype", "INT"),
        new ConfigColumnMeta(5, "param_1", "STRING"),
        new ConfigColumnMeta(6, "param_2", "STRING"),
        new ConfigColumnMeta(7, "param_3", "STRING"),
        new ConfigColumnMeta(8, "param_4", "STRING"),
        new ConfigColumnMeta(9, "loopNum", "INT"));
  }
}
