package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DungeonBuffConfigCheckerBase extends AbstractConfigChecker<DungeonBuffConfig> {
  @Override
  public String getConfigFileName() {
    return "dungeonBuff.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "kind", "int"),
        new ConfigColumnMeta(3, "group", "int"),
        new ConfigColumnMeta(4, "level", "int"),
        new ConfigColumnMeta(5, "turn", "int"),
        new ConfigColumnMeta(6, "effectType", "int"),
        new ConfigColumnMeta(7, "buffId", "int"),
        new ConfigColumnMeta(8, "discribe", "string"),
        new ConfigColumnMeta(9, "icon", "int"),
        new ConfigColumnMeta(10, "iconBig", "int"),
        new ConfigColumnMeta(11, "floor", "int"),
        new ConfigColumnMeta(12, "frame", "int"),
        new ConfigColumnMeta(13, "heroType", "int"),
        new ConfigColumnMeta(14, "assembly", "int"));
  }
}
