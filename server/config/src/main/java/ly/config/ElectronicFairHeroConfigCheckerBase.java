package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ElectronicFairHeroConfigCheckerBase extends AbstractConfigChecker<ElectronicFairHeroConfig> {
  @Override
  public String getConfigFileName() {
    return "electronicFairHero.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "heroId", "int"),
        new ConfigColumnMeta(2, "Level", "int"),
        new ConfigColumnMeta(3, "star", "int"),
        new ConfigColumnMeta(4, "awaken", "int"),
        new ConfigColumnMeta(5, "skill1", "int"),
        new ConfigColumnMeta(6, "skill2", "int"),
        new ConfigColumnMeta(7, "skill3", "int"),
        new ConfigColumnMeta(8, "skillS", "int"),
        new ConfigColumnMeta(9, "maxHP", "int"),
        new ConfigColumnMeta(10, "attack", "int"),
        new ConfigColumnMeta(11, "defence", "int"),
        new ConfigColumnMeta(12, "speed", "int"),
        new ConfigColumnMeta(13, "crit", "int"),
        new ConfigColumnMeta(14, "critRatio", "int"),
        new ConfigColumnMeta(15, "effectHit", "int"),
        new ConfigColumnMeta(16, "effectDodge", "int"),
        new ConfigColumnMeta(17, "spCoe", "int"));
  }
}
