package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ElectronicThreeHeroConfigCheckerBase extends AbstractConfigChecker<ElectronicThreeHeroConfig> {
  @Override
  public String getConfigFileName() {
    return "electronicThreeHero.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "heroId", "INT"),
        new ConfigColumnMeta(2, "heroDesc", "STRING"),
        new ConfigColumnMeta(3, "pro", "INT"),
        new ConfigColumnMeta(4, "group", "INT"),
        new ConfigColumnMeta(5, "circuitAttr1", "STRING"),
        new ConfigColumnMeta(6, "circuit2", "INT"),
        new ConfigColumnMeta(7, "circuitAttr2", "STRING"),
        new ConfigColumnMeta(8, "circuit3", "INT"),
        new ConfigColumnMeta(9, "circuitAttr3", "STRING"),
        new ConfigColumnMeta(10, "circuit4", "INT"),
        new ConfigColumnMeta(11, "circuitAttr4", "STRING"),
        new ConfigColumnMeta(12, "circuit5", "INT"),
        new ConfigColumnMeta(13, "circuitAttr5", "STRING"),
        new ConfigColumnMeta(14, "circuit6", "INT"),
        new ConfigColumnMeta(15, "circuitAttr6", "STRING"),
        new ConfigColumnMeta(16, "circuit7", "INT"),
        new ConfigColumnMeta(17, "circuitAttr7", "STRING"),
        new ConfigColumnMeta(18, "maxHP", "INT"),
        new ConfigColumnMeta(19, "attack", "INT"),
        new ConfigColumnMeta(20, "defence", "INT"),
        new ConfigColumnMeta(21, "speed", "INT"),
        new ConfigColumnMeta(22, "crit", "INT"),
        new ConfigColumnMeta(23, "critRatio", "INT"),
        new ConfigColumnMeta(24, "effectHit", "INT"),
        new ConfigColumnMeta(25, "effectDodge", "INT"),
        new ConfigColumnMeta(26, "spCoe", "INT"));
  }
}
