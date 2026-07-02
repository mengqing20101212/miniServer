package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CharacterskillsConfigCheckerBase extends AbstractConfigChecker<CharacterskillsConfig> {
  @Override
  public String getConfigFileName() {
    return "characterskills.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "heroid", "INT"),
        new ConfigColumnMeta(2, "config_name", "STRING"),
        new ConfigColumnMeta(3, "headResource_S", "INT"),
        new ConfigColumnMeta(4, "bgResource", "INT"),
        new ConfigColumnMeta(5, "bgResource_0", "INT"),
        new ConfigColumnMeta(6, "skillResource", "INT"),
        new ConfigColumnMeta(7, "effectResource_1", "INT"),
        new ConfigColumnMeta(8, "effectResource_2", "INT"),
        new ConfigColumnMeta(9, "color", "STRING"));
  }
}
