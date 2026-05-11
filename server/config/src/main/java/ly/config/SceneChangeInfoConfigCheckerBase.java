package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SceneChangeInfoConfigCheckerBase extends AbstractConfigChecker<SceneChangeInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "sceneChangeInfo.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "hpInherit", "BOOL"),
        new ConfigColumnMeta(2, "spInherit", "BOOL"),
        new ConfigColumnMeta(3, "energyInherit", "BOOL"),
        new ConfigColumnMeta(4, "energyBarInherit", "BOOL"),
        new ConfigColumnMeta(5, "sPowerInherit", "BOOL"),
        new ConfigColumnMeta(6, "actionBarInherit", "BOOL"),
        new ConfigColumnMeta(7, "skillCDInherit", "BOOL"),
        new ConfigColumnMeta(8, "bonusInherit", "BOOL"),
        new ConfigColumnMeta(9, "buffInherit", "BOOL"),
        new ConfigColumnMeta(10, "globalRound", "BOOL"),
        new ConfigColumnMeta(11, "campRound", "BOOL"),
        new ConfigColumnMeta(12, "charRound", "BOOL"));
  }
}
