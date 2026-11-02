package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CharacterskillsConfigCheckerBase extends AbstractConfigChecker<CharacterskillsConfig> {
  @Override
  public String getConfigFileName() {
    return "characterskills.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "heroid", "int"),
        new ConfigColumnMeta(2, "config_name", "string"),
        new ConfigColumnMeta(3, "headResource_S", "int"),
        new ConfigColumnMeta(4, "bgResource", "int"),
        new ConfigColumnMeta(5, "bgResource_0", "int"),
        new ConfigColumnMeta(6, "skillResource", "int"),
        new ConfigColumnMeta(7, "effectResource_1", "int"),
        new ConfigColumnMeta(8, "effectResource_2", "int"),
        new ConfigColumnMeta(9, "color", "string"));
  }
}
