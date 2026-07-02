package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SocietyCleanCityStoryConfigCheckerBase extends AbstractConfigChecker<SocietyCleanCityStoryConfig> {
  @Override
  public String getConfigFileName() {
    return "societyCleanCityStory.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "group", "INT"),
        new ConfigColumnMeta(3, "para", "INT"),
        new ConfigColumnMeta(4, "questionPool", "STRING"),
        new ConfigColumnMeta(5, "dropSuccess", "INT"),
        new ConfigColumnMeta(6, "dropFail", "INT"));
  }
}
