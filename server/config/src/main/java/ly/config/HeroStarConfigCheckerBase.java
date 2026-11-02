package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroStarConfigCheckerBase extends AbstractConfigChecker<HeroStarConfig> {
  @Override
  public String getConfigFileName() {
    return "heroStar.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "star", "int"),
        new ConfigColumnMeta(1, "modelId", "int"),
        new ConfigColumnMeta(2, "maxLevel", "int"),
        new ConfigColumnMeta(3, "awakenLevel", "int"),
        new ConfigColumnMeta(4, "starItem", "string"),
        new ConfigColumnMeta(5, "starItem2", "string"),
        new ConfigColumnMeta(6, "retainItem", "string"),
        new ConfigColumnMeta(7, "currencyType", "int"),
        new ConfigColumnMeta(8, "currencyNum", "string"),
        new ConfigColumnMeta(9, "item", "string"),
        new ConfigColumnMeta(10, "circuitSlot", "string"),
        new ConfigColumnMeta(11, "followAwaken", "int"));
  }
}
