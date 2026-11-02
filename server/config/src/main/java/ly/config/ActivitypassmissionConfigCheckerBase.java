package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitypassmissionConfigCheckerBase extends AbstractConfigChecker<ActivitypassmissionConfig> {
  @Override
  public String getConfigFileName() {
    return "activitypassmission.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "scheDuling", "int"),
        new ConfigColumnMeta(2, "questId", "int"),
        new ConfigColumnMeta(3, "missinType", "int"),
        new ConfigColumnMeta(4, "minLevel", "int"),
        new ConfigColumnMeta(5, "maxLevel", "int"),
        new ConfigColumnMeta(6, "name", "string"),
        new ConfigColumnMeta(7, "point", "int"),
        new ConfigColumnMeta(8, "redirectionId", "int"),
        new ConfigColumnMeta(9, "goundId", "int"),
        new ConfigColumnMeta(10, "weights", "int"));
  }
}
