package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityCommendgiftConfigCheckerBase extends AbstractConfigChecker<ActivityCommendgiftConfig> {
  @Override
  public String getConfigFileName() {
    return "activityCommendgift.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "CommendLevel", "INT"),
        new ConfigColumnMeta(2, "Commendtype", "INT"),
        new ConfigColumnMeta(3, "RewardShow", "INT"),
        new ConfigColumnMeta(4, "drop", "INT"),
        new ConfigColumnMeta(5, "rechargShow", "INT"),
        new ConfigColumnMeta(6, "rechargdrop", "INT"));
  }
}
