package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuildBossConfigCheckerBase extends AbstractConfigChecker<GuildBossConfig> {
  @Override
  public String getConfigFileName() {
    return "guildBoss.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "sceneId", "INT"),
        new ConfigColumnMeta(2, "level", "INT"),
        new ConfigColumnMeta(3, "group", "INT"),
        new ConfigColumnMeta(4, "icon", "INT"),
        new ConfigColumnMeta(5, "battleReward", "STRING"),
        new ConfigColumnMeta(6, "killReward", "INT"),
        new ConfigColumnMeta(7, "disPlayBattleReward", "STRING"),
        new ConfigColumnMeta(8, "disPlayKillReward", "STRING"),
        new ConfigColumnMeta(9, "bossDesc", "STRING"),
        new ConfigColumnMeta(10, "battleDesc", "STRING"),
        new ConfigColumnMeta(11, "name", "STRING"),
        new ConfigColumnMeta(12, "bossId", "INT"),
        new ConfigColumnMeta(13, "bossSkill", "LIST<INT>"),
        new ConfigColumnMeta(14, "bossDamageStatisticsType", "INT"));
  }
}
