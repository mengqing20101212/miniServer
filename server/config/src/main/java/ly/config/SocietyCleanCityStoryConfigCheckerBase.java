package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SocietyCleanCityStoryConfigCheckerBase extends AbstractConfigChecker<SocietyCleanCityStoryConfig> {
  @Override
  public String getConfigFileName() {
    return "societyCleanCityStory.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "group", "int"),
        new ConfigColumnMeta(3, "para", "int"),
        new ConfigColumnMeta(4, "questionPool", "string"),
        new ConfigColumnMeta(5, "dropSuccess", "int"),
        new ConfigColumnMeta(6, "dropFail", "int"));
  }
}
