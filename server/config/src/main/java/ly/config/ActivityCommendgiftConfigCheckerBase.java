package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityCommendgiftConfigCheckerBase extends AbstractConfigChecker<ActivityCommendgiftConfig> {
  @Override
  public String getConfigFileName() {
    return "activityCommendgift.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "CommendLevel", "int"),
        new ConfigColumnMeta(2, "Commendtype", "int"),
        new ConfigColumnMeta(3, "RewardShow", "int"),
        new ConfigColumnMeta(4, "drop", "int"),
        new ConfigColumnMeta(5, "rechargShow", "int"),
        new ConfigColumnMeta(6, "rechargdrop", "int"));
  }
}
