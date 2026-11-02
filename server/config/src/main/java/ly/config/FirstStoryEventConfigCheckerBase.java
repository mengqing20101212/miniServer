package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class FirstStoryEventConfigCheckerBase extends AbstractConfigChecker<FirstStoryEventConfig> {
  @Override
  public String getConfigFileName() {
    return "firstStoryEvent.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "type", "int"),
        new ConfigColumnMeta(3, "values", "string"),
        new ConfigColumnMeta(4, "flag", "int"),
        new ConfigColumnMeta(5, "transitionType", "int"));
  }
}
