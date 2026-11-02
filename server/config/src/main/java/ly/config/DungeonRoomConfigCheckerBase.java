package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DungeonRoomConfigCheckerBase extends AbstractConfigChecker<DungeonRoomConfig> {
  @Override
  public String getConfigFileName() {
    return "dungeonRoom.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "mainId", "int"),
        new ConfigColumnMeta(2, "levelId", "int"),
        new ConfigColumnMeta(3, "lineId", "int"));
  }
}
