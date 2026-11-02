package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SocietyCleanCityBossConfigCheckerBase extends AbstractConfigChecker<SocietyCleanCityBossConfig> {
  @Override
  public String getConfigFileName() {
    return "societyCleanCityBoss.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "sceneId", "int"),
        new ConfigColumnMeta(2, "battleReward", "string"),
        new ConfigColumnMeta(3, "killReward", "int"),
        new ConfigColumnMeta(4, "disPlayBattleReward", "string"),
        new ConfigColumnMeta(5, "disPlayKillReward", "string"),
        new ConfigColumnMeta(6, "bossId", "int"),
        new ConfigColumnMeta(7, "bossDamageStatisticsType", "int"),
        new ConfigColumnMeta(8, "spineModelResId", "int"),
        new ConfigColumnMeta(9, "spineAnimation", "string"),
        new ConfigColumnMeta(10, "scale", "int"),
        new ConfigColumnMeta(11, "coordinate", "string"));
  }
}
