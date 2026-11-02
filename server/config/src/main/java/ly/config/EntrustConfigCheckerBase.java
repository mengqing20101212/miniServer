package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class EntrustConfigCheckerBase extends AbstractConfigChecker<EntrustConfig> {
  @Override
  public String getConfigFileName() {
    return "entrust.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "groupId", "int"),
        new ConfigColumnMeta(2, "star", "int"),
        new ConfigColumnMeta(3, "unlockType", "int"),
        new ConfigColumnMeta(4, "unlockCondition", "int"),
        new ConfigColumnMeta(5, "unlockDec", "string"),
        new ConfigColumnMeta(6, "reward", "string"),
        new ConfigColumnMeta(7, "drop", "int"),
        new ConfigColumnMeta(8, "dropPercent", "int"),
        new ConfigColumnMeta(9, "time", "int"),
        new ConfigColumnMeta(10, "type", "int"),
        new ConfigColumnMeta(11, "percent", "int"),
        new ConfigColumnMeta(12, "heroNum", "int"),
        new ConfigColumnMeta(13, "taskName", "string"),
        new ConfigColumnMeta(14, "timeGroup", "string"),
        new ConfigColumnMeta(15, "action", "string"),
        new ConfigColumnMeta(16, "position", "string"),
        new ConfigColumnMeta(17, "iconResId", "int"));
  }
}
