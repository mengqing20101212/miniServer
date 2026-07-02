package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Battle_typeConfigCheckerBase extends AbstractConfigChecker<Battle_typeConfig> {
  @Override
  public String getConfigFileName() {
    return "battle_type.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "name", "STRING"),
        new ConfigColumnMeta(1, "ms", "INT"),
        new ConfigColumnMeta(2, "timeout", "INT"),
        new ConfigColumnMeta(3, "battle_time", "INT"),
        new ConfigColumnMeta(4, "battle_type", "INT"),
        new ConfigColumnMeta(5, "sceneid", "INT"),
        new ConfigColumnMeta(6, "preparetimeout", "INT"),
        new ConfigColumnMeta(7, "checktimeoutinterval", "INT"),
        new ConfigColumnMeta(8, "accounttimeout", "INT"));
  }
}
