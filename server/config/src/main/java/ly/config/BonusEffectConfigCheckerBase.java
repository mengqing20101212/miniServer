package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class BonusEffectConfigCheckerBase extends AbstractConfigChecker<BonusEffectConfig> {
  @Override
  public String getConfigFileName() {
    return "bonusEffect.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "description", "string"),
        new ConfigColumnMeta(2, "icon", "int"),
        new ConfigColumnMeta(3, "bonusEffect", "int"),
        new ConfigColumnMeta(4, "isBonusBuff", "bool"),
        new ConfigColumnMeta(5, "bonusType", "string"),
        new ConfigColumnMeta(6, "param_1", "string"),
        new ConfigColumnMeta(7, "isVanish", "string"),
        new ConfigColumnMeta(8, "group", "int"),
        new ConfigColumnMeta(9, "level", "int"));
  }
}
