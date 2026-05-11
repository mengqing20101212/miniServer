package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuildChoiceHeroConfigCheckerBase extends AbstractConfigChecker<GuildChoiceHeroConfig> {
  @Override
  public String getConfigFileName() {
    return "guildChoiceHero.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "unlcokLevel", "INT"),
        new ConfigColumnMeta(2, "npcid", "INT"),
        new ConfigColumnMeta(3, "playerLv", "INT"),
        new ConfigColumnMeta(4, "sceneId", "INT"),
        new ConfigColumnMeta(5, "priority", "INT"),
        new ConfigColumnMeta(6, "reward1", "INT"),
        new ConfigColumnMeta(7, "reward2", "STRING"),
        new ConfigColumnMeta(8, "bossSkill", "LIST<INT>"),
        new ConfigColumnMeta(9, "dropShow", "INT"),
        new ConfigColumnMeta(10, "bossDamageStatisticsType", "INT"));
  }
}
