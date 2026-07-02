package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityTaskConfigCheckerBase extends AbstractConfigChecker<ActivityTaskConfig> {
  @Override
  public String getConfigFileName() {
    return "activityTask.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "title", "STRING"),
        new ConfigColumnMeta(2, "name", "STRING"),
        new ConfigColumnMeta(3, "questType", "INT"),
        new ConfigColumnMeta(4, "scheDuling", "INT"),
        new ConfigColumnMeta(5, "page", "INT"),
        new ConfigColumnMeta(6, "condition", "INT"),
        new ConfigColumnMeta(7, "rewardShow", "STRING"),
        new ConfigColumnMeta(8, "drop", "INT"),
        new ConfigColumnMeta(9, "redirectionId", "INT"),
        new ConfigColumnMeta(10, "priority", "INT"),
        new ConfigColumnMeta(11, "pointType", "INT"),
        new ConfigColumnMeta(12, "point", "INT"),
        new ConfigColumnMeta(13, "startTime", "STRING"),
        new ConfigColumnMeta(14, "endTime", "STRING"),
        new ConfigColumnMeta(15, "titlePicId", "INT"));
  }
}
