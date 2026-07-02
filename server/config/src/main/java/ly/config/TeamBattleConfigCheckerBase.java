package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class TeamBattleConfigCheckerBase extends AbstractConfigChecker<TeamBattleConfig> {
  @Override
  public String getConfigFileName() {
    return "teamBattle.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "activityId", "INT"),
        new ConfigColumnMeta(3, "level", "INT"),
        new ConfigColumnMeta(4, "name", "STRING"),
        new ConfigColumnMeta(5, "dungeonConfigType", "INT"),
        new ConfigColumnMeta(6, "dungeonConfigId", "INT"),
        new ConfigColumnMeta(7, "sceneId", "INT"),
        new ConfigColumnMeta(8, "isDynamic", "BOOL"),
        new ConfigColumnMeta(9, "isOut", "INT"),
        new ConfigColumnMeta(10, "stageType", "INT"));
  }
}
