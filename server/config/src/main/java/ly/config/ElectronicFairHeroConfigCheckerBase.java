package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ElectronicFairHeroConfigCheckerBase extends AbstractConfigChecker<ElectronicFairHeroConfig> {
  @Override
  public String getConfigFileName() {
    return "electronicFairHero.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "heroId", "INT"),
        new ConfigColumnMeta(2, "Level", "INT"),
        new ConfigColumnMeta(3, "star", "INT"),
        new ConfigColumnMeta(4, "awaken", "INT"),
        new ConfigColumnMeta(5, "skill1", "INT"),
        new ConfigColumnMeta(6, "skill2", "INT"),
        new ConfigColumnMeta(7, "skill3", "INT"),
        new ConfigColumnMeta(8, "skillS", "INT"),
        new ConfigColumnMeta(9, "maxHP", "INT"),
        new ConfigColumnMeta(10, "attack", "INT"),
        new ConfigColumnMeta(11, "defence", "INT"),
        new ConfigColumnMeta(12, "speed", "INT"),
        new ConfigColumnMeta(13, "crit", "INT"),
        new ConfigColumnMeta(14, "critRatio", "INT"),
        new ConfigColumnMeta(15, "effectHit", "INT"),
        new ConfigColumnMeta(16, "effectDodge", "INT"),
        new ConfigColumnMeta(17, "spCoe", "INT"));
  }
}
