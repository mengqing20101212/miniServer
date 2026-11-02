package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityTaskConfigCheckerBase extends AbstractConfigChecker<ActivityTaskConfig> {
  @Override
  public String getConfigFileName() {
    return "activityTask.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "title", "string"),
        new ConfigColumnMeta(2, "name", "string"),
        new ConfigColumnMeta(3, "questType", "int"),
        new ConfigColumnMeta(4, "scheDuling", "int"),
        new ConfigColumnMeta(5, "page", "int"),
        new ConfigColumnMeta(6, "condition", "int"),
        new ConfigColumnMeta(7, "rewardShow", "string"),
        new ConfigColumnMeta(8, "drop", "int"),
        new ConfigColumnMeta(9, "redirectionId", "int"),
        new ConfigColumnMeta(10, "priority", "int"),
        new ConfigColumnMeta(11, "pointType", "int"),
        new ConfigColumnMeta(12, "point", "int"),
        new ConfigColumnMeta(13, "startTime", "string"),
        new ConfigColumnMeta(14, "endTime", "string"),
        new ConfigColumnMeta(15, "titlePicId", "int"));
  }
}
