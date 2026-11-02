package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuildChoiceHeroConfigCheckerBase extends AbstractConfigChecker<GuildChoiceHeroConfig> {
  @Override
  public String getConfigFileName() {
    return "guildChoiceHero.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "unlcokLevel", "int"),
        new ConfigColumnMeta(2, "npcid", "int"),
        new ConfigColumnMeta(3, "playerLv", "int"),
        new ConfigColumnMeta(4, "sceneId", "int"),
        new ConfigColumnMeta(5, "priority", "int"),
        new ConfigColumnMeta(6, "reward1", "int"),
        new ConfigColumnMeta(7, "reward2", "string"),
        new ConfigColumnMeta(8, "bossSkill", "list<int>"),
        new ConfigColumnMeta(9, "dropShow", "int"),
        new ConfigColumnMeta(10, "bossDamageStatisticsType", "int"));
  }
}
