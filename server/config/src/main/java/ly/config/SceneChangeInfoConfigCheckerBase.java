package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SceneChangeInfoConfigCheckerBase extends AbstractConfigChecker<SceneChangeInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "sceneChangeInfo.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "hpInherit", "bool"),
        new ConfigColumnMeta(2, "spInherit", "bool"),
        new ConfigColumnMeta(3, "energyInherit", "bool"),
        new ConfigColumnMeta(4, "energyBarInherit", "bool"),
        new ConfigColumnMeta(5, "sPowerInherit", "bool"),
        new ConfigColumnMeta(6, "actionBarInherit", "bool"),
        new ConfigColumnMeta(7, "skillCDInherit", "bool"),
        new ConfigColumnMeta(8, "bonusInherit", "bool"),
        new ConfigColumnMeta(9, "buffInherit", "bool"),
        new ConfigColumnMeta(10, "globalRound", "bool"),
        new ConfigColumnMeta(11, "campRound", "bool"),
        new ConfigColumnMeta(12, "charRound", "bool"));
  }
}
