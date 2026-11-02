package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class TeamBattleConfigCheckerBase extends AbstractConfigChecker<TeamBattleConfig> {
  @Override
  public String getConfigFileName() {
    return "teamBattle.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "activityId", "int"),
        new ConfigColumnMeta(3, "level", "int"),
        new ConfigColumnMeta(4, "name", "string"),
        new ConfigColumnMeta(5, "dungeonConfigType", "int"),
        new ConfigColumnMeta(6, "dungeonConfigId", "int"),
        new ConfigColumnMeta(7, "sceneId", "int"),
        new ConfigColumnMeta(8, "isDynamic", "bool"),
        new ConfigColumnMeta(9, "isOut", "int"),
        new ConfigColumnMeta(10, "stageType", "int"));
  }
}
