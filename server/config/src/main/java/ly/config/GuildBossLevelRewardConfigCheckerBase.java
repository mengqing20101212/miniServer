package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuildBossLevelRewardConfigCheckerBase extends AbstractConfigChecker<GuildBossLevelRewardConfig> {
  @Override
  public String getConfigFileName() {
    return "guildBossLevelReward.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "level", "INT"),
        new ConfigColumnMeta(2, "levelReward", "INT"),
        new ConfigColumnMeta(3, "disPlayLevelReward", "STRING"));
  }
}
