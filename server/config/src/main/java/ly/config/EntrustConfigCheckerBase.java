package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class EntrustConfigCheckerBase extends AbstractConfigChecker<EntrustConfig> {
  @Override
  public String getConfigFileName() {
    return "entrust.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "groupId", "INT"),
        new ConfigColumnMeta(2, "star", "INT"),
        new ConfigColumnMeta(3, "unlockType", "INT"),
        new ConfigColumnMeta(4, "unlockCondition", "INT"),
        new ConfigColumnMeta(5, "unlockDec", "STRING"),
        new ConfigColumnMeta(6, "reward", "STRING"),
        new ConfigColumnMeta(7, "drop", "INT"),
        new ConfigColumnMeta(8, "dropPercent", "INT"),
        new ConfigColumnMeta(9, "time", "INT"),
        new ConfigColumnMeta(10, "type", "INT"),
        new ConfigColumnMeta(11, "percent", "INT"),
        new ConfigColumnMeta(12, "heroNum", "INT"),
        new ConfigColumnMeta(13, "taskName", "STRING"),
        new ConfigColumnMeta(14, "timeGroup", "STRING"),
        new ConfigColumnMeta(15, "action", "STRING"),
        new ConfigColumnMeta(16, "position", "STRING"),
        new ConfigColumnMeta(17, "iconResId", "INT"));
  }
}
