package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuildBossConfigCheckerBase extends AbstractConfigChecker<GuildBossConfig> {
  @Override
  public String getConfigFileName() {
    return "guildBoss.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "sceneId", "int"),
        new ConfigColumnMeta(2, "level", "int"),
        new ConfigColumnMeta(3, "group", "int"),
        new ConfigColumnMeta(4, "icon", "int"),
        new ConfigColumnMeta(5, "battleReward", "string"),
        new ConfigColumnMeta(6, "killReward", "int"),
        new ConfigColumnMeta(7, "disPlayBattleReward", "string"),
        new ConfigColumnMeta(8, "disPlayKillReward", "string"),
        new ConfigColumnMeta(9, "bossDesc", "string"),
        new ConfigColumnMeta(10, "battleDesc", "string"),
        new ConfigColumnMeta(11, "name", "string"),
        new ConfigColumnMeta(12, "bossId", "int"),
        new ConfigColumnMeta(13, "bossSkill", "list<int>"),
        new ConfigColumnMeta(14, "bossDamageStatisticsType", "int"));
  }
}
