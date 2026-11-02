package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Battle_typeConfigCheckerBase extends AbstractConfigChecker<Battle_typeConfig> {
  @Override
  public String getConfigFileName() {
    return "battle_type.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "name", "string"),
        new ConfigColumnMeta(1, "ms", "int"),
        new ConfigColumnMeta(2, "timeout", "int"),
        new ConfigColumnMeta(3, "battle_time", "int"),
        new ConfigColumnMeta(4, "battle_type", "int"),
        new ConfigColumnMeta(5, "sceneid", "int"),
        new ConfigColumnMeta(6, "preparetimeout", "int"),
        new ConfigColumnMeta(7, "checktimeoutinterval", "int"),
        new ConfigColumnMeta(8, "accounttimeout", "int"));
  }
}
