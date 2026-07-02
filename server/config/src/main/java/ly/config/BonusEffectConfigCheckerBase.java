package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class BonusEffectConfigCheckerBase extends AbstractConfigChecker<BonusEffectConfig> {
  @Override
  public String getConfigFileName() {
    return "bonusEffect.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "description", "STRING"),
        new ConfigColumnMeta(2, "icon", "INT"),
        new ConfigColumnMeta(3, "bonusEffect", "INT"),
        new ConfigColumnMeta(4, "isBonusBuff", "BOOL"),
        new ConfigColumnMeta(5, "bonusType", "STRING"),
        new ConfigColumnMeta(6, "param_1", "STRING"),
        new ConfigColumnMeta(7, "isVanish", "STRING"),
        new ConfigColumnMeta(8, "group", "INT"),
        new ConfigColumnMeta(9, "level", "INT"));
  }
}
