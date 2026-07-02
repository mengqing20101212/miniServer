package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class MainStoryEventConfigCheckerBase extends AbstractConfigChecker<MainStoryEventConfig> {
  @Override
  public String getConfigFileName() {
    return "mainStoryEvent.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "type", "INT"),
        new ConfigColumnMeta(3, "values", "STRING"),
        new ConfigColumnMeta(4, "group", "INT"),
        new ConfigColumnMeta(5, "sceneResource", "STRING"),
        new ConfigColumnMeta(6, "loseContinue", "INT"),
        new ConfigColumnMeta(7, "transitionType", "INT"),
        new ConfigColumnMeta(8, "isPreload", "INT"));
  }
}
