package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DungeonLevelConfigCheckerBase extends AbstractConfigChecker<DungeonLevelConfig> {
  @Override
  public String getConfigFileName() {
    return "dungeonLevel.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "leftInterval", "INT"),
        new ConfigColumnMeta(2, "rightInterval", "INT"));
  }
}
