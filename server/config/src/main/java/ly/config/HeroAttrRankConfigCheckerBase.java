package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroAttrRankConfigCheckerBase extends AbstractConfigChecker<HeroAttrRankConfig> {
  @Override
  public String getConfigFileName() {
    return "heroAttrRank.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "des", "STRING"),
        new ConfigColumnMeta(2, "maxHP", "INT"),
        new ConfigColumnMeta(3, "attack", "INT"),
        new ConfigColumnMeta(4, "defence", "INT"),
        new ConfigColumnMeta(5, "speed", "INT"),
        new ConfigColumnMeta(6, "crit", "INT"),
        new ConfigColumnMeta(7, "critRatio", "INT"),
        new ConfigColumnMeta(8, "effectHit", "INT"),
        new ConfigColumnMeta(9, "effectDodge", "INT"),
        new ConfigColumnMeta(10, "spCoe", "INT"));
  }
}
