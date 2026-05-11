package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CircuitItemInfoConfigCheckerBase extends AbstractConfigChecker<CircuitItemInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "circuitItemInfo.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "description", "STRING"),
        new ConfigColumnMeta(2, "type", "INT"),
        new ConfigColumnMeta(3, "school", "INT"),
        new ConfigColumnMeta(4, "grade", "INT"),
        new ConfigColumnMeta(5, "sequence", "INT"),
        new ConfigColumnMeta(6, "pos", "INT"),
        new ConfigColumnMeta(7, "suitIds", "STRING"),
        new ConfigColumnMeta(8, "mainAttrPoolId", "INT"),
        new ConfigColumnMeta(9, "subAttrPoolId", "INT"),
        new ConfigColumnMeta(10, "subAttrUpgradePoolId", "INT"),
        new ConfigColumnMeta(11, "upgradeType", "INT"),
        new ConfigColumnMeta(12, "decomposeGold", "INT"),
        new ConfigColumnMeta(13, "decomposeDG", "INT"),
        new ConfigColumnMeta(14, "quality", "INT"),
        new ConfigColumnMeta(15, "lightAttrPoolId", "INT"));
  }
}
