package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class MainStoryEventConfigCheckerBase extends AbstractConfigChecker<MainStoryEventConfig> {
  @Override
  public String getConfigFileName() {
    return "mainStoryEvent.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "type", "int"),
        new ConfigColumnMeta(3, "values", "string"),
        new ConfigColumnMeta(4, "group", "int"),
        new ConfigColumnMeta(5, "sceneResource", "string"),
        new ConfigColumnMeta(6, "loseContinue", "int"),
        new ConfigColumnMeta(7, "transitionType", "int"),
        new ConfigColumnMeta(8, "isPreload", "int"));
  }
}
