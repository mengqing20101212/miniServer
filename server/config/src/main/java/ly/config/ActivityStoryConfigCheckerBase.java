package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityStoryConfigCheckerBase extends AbstractConfigChecker<ActivityStoryConfig> {
  @Override
  public String getConfigFileName() {
    return "activityStory.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "scheDuling", "int"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "picture", "string"),
        new ConfigColumnMeta(4, "holographic", "int"),
        new ConfigColumnMeta(5, "activtyId", "int"),
        new ConfigColumnMeta(6, "trueActivityId", "int"));
  }
}
