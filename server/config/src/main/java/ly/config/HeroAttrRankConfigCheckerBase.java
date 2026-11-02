package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroAttrRankConfigCheckerBase extends AbstractConfigChecker<HeroAttrRankConfig> {
  @Override
  public String getConfigFileName() {
    return "heroAttrRank.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "des", "string"),
        new ConfigColumnMeta(2, "maxHP", "int"),
        new ConfigColumnMeta(3, "attack", "int"),
        new ConfigColumnMeta(4, "defence", "int"),
        new ConfigColumnMeta(5, "speed", "int"),
        new ConfigColumnMeta(6, "crit", "int"),
        new ConfigColumnMeta(7, "critRatio", "int"),
        new ConfigColumnMeta(8, "effectHit", "int"),
        new ConfigColumnMeta(9, "effectDodge", "int"),
        new ConfigColumnMeta(10, "spCoe", "int"));
  }
}
