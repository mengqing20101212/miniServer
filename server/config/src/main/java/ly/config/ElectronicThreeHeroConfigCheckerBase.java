package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ElectronicThreeHeroConfigCheckerBase extends AbstractConfigChecker<ElectronicThreeHeroConfig> {
  @Override
  public String getConfigFileName() {
    return "electronicThreeHero.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "heroId", "int"),
        new ConfigColumnMeta(2, "heroDesc", "string"),
        new ConfigColumnMeta(3, "pro", "int"),
        new ConfigColumnMeta(4, "group", "int"),
        new ConfigColumnMeta(5, "circuitAttr1", "string"),
        new ConfigColumnMeta(6, "circuit2", "int"),
        new ConfigColumnMeta(7, "circuitAttr2", "string"),
        new ConfigColumnMeta(8, "circuit3", "int"),
        new ConfigColumnMeta(9, "circuitAttr3", "string"),
        new ConfigColumnMeta(10, "circuit4", "int"),
        new ConfigColumnMeta(11, "circuitAttr4", "string"),
        new ConfigColumnMeta(12, "circuit5", "int"),
        new ConfigColumnMeta(13, "circuitAttr5", "string"),
        new ConfigColumnMeta(14, "circuit6", "int"),
        new ConfigColumnMeta(15, "circuitAttr6", "string"),
        new ConfigColumnMeta(16, "circuit7", "int"),
        new ConfigColumnMeta(17, "circuitAttr7", "string"),
        new ConfigColumnMeta(18, "maxHP", "int"),
        new ConfigColumnMeta(19, "attack", "int"),
        new ConfigColumnMeta(20, "defence", "int"),
        new ConfigColumnMeta(21, "speed", "int"),
        new ConfigColumnMeta(22, "crit", "int"),
        new ConfigColumnMeta(23, "critRatio", "int"),
        new ConfigColumnMeta(24, "effectHit", "int"),
        new ConfigColumnMeta(25, "effectDodge", "int"),
        new ConfigColumnMeta(26, "spCoe", "int"));
  }
}
