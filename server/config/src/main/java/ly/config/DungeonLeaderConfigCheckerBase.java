package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DungeonLeaderConfigCheckerBase extends AbstractConfigChecker<DungeonLeaderConfig> {
  @Override
  public String getConfigFileName() {
    return "dungeonLeader.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "para", "INT"),
        new ConfigColumnMeta(3, "type", "INT"),
        new ConfigColumnMeta(4, "leaderEffectContent", "STRING"),
        new ConfigColumnMeta(5, "bodyPic", "INT"));
  }
}
