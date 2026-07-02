package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DungeonBuffConfigCheckerBase extends AbstractConfigChecker<DungeonBuffConfig> {
  @Override
  public String getConfigFileName() {
    return "dungeonBuff.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "kind", "INT"),
        new ConfigColumnMeta(3, "group", "INT"),
        new ConfigColumnMeta(4, "level", "INT"),
        new ConfigColumnMeta(5, "turn", "INT"),
        new ConfigColumnMeta(6, "effectType", "INT"),
        new ConfigColumnMeta(7, "buffId", "INT"),
        new ConfigColumnMeta(8, "discribe", "STRING"),
        new ConfigColumnMeta(9, "icon", "INT"),
        new ConfigColumnMeta(10, "iconBig", "INT"),
        new ConfigColumnMeta(11, "floor", "INT"),
        new ConfigColumnMeta(12, "frame", "INT"),
        new ConfigColumnMeta(13, "heroType", "INT"),
        new ConfigColumnMeta(14, "assembly", "INT"));
  }
}
