package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitypassmissionConfigCheckerBase extends AbstractConfigChecker<ActivitypassmissionConfig> {
  @Override
  public String getConfigFileName() {
    return "activitypassmission.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "scheDuling", "INT"),
        new ConfigColumnMeta(2, "questId", "INT"),
        new ConfigColumnMeta(3, "missinType", "INT"),
        new ConfigColumnMeta(4, "minLevel", "INT"),
        new ConfigColumnMeta(5, "maxLevel", "INT"),
        new ConfigColumnMeta(6, "name", "STRING"),
        new ConfigColumnMeta(7, "point", "INT"),
        new ConfigColumnMeta(8, "redirectionId", "INT"),
        new ConfigColumnMeta(9, "goundId", "INT"),
        new ConfigColumnMeta(10, "weights", "INT"));
  }
}
