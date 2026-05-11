package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SocietyCleanCityBossConfigCheckerBase extends AbstractConfigChecker<SocietyCleanCityBossConfig> {
  @Override
  public String getConfigFileName() {
    return "societyCleanCityBoss.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "sceneId", "INT"),
        new ConfigColumnMeta(2, "battleReward", "STRING"),
        new ConfigColumnMeta(3, "killReward", "INT"),
        new ConfigColumnMeta(4, "disPlayBattleReward", "STRING"),
        new ConfigColumnMeta(5, "disPlayKillReward", "STRING"),
        new ConfigColumnMeta(6, "bossId", "INT"),
        new ConfigColumnMeta(7, "bossDamageStatisticsType", "INT"),
        new ConfigColumnMeta(8, "spineModelResId", "INT"),
        new ConfigColumnMeta(9, "spineAnimation", "STRING"),
        new ConfigColumnMeta(10, "scale", "INT"),
        new ConfigColumnMeta(11, "coordinate", "STRING"));
  }
}
