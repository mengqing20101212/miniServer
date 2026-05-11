package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class HeroStarConfigCheckerBase extends AbstractConfigChecker<HeroStarConfig> {
  @Override
  public String getConfigFileName() {
    return "heroStar.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "star", "INT"),
        new ConfigColumnMeta(1, "modelId", "INT"),
        new ConfigColumnMeta(2, "maxLevel", "INT"),
        new ConfigColumnMeta(3, "awakenLevel", "INT"),
        new ConfigColumnMeta(4, "starItem", "STRING"),
        new ConfigColumnMeta(5, "starItem2", "STRING"),
        new ConfigColumnMeta(6, "retainItem", "STRING"),
        new ConfigColumnMeta(7, "currencyType", "INT"),
        new ConfigColumnMeta(8, "currencyNum", "STRING"),
        new ConfigColumnMeta(9, "item", "STRING"),
        new ConfigColumnMeta(10, "circuitSlot", "STRING"),
        new ConfigColumnMeta(11, "followAwaken", "INT"));
  }
}
