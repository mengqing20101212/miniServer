package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CircuitItemInfoConfigCheckerBase extends AbstractConfigChecker<CircuitItemInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "circuitItemInfo.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "description", "string"),
        new ConfigColumnMeta(2, "type", "int"),
        new ConfigColumnMeta(3, "school", "int"),
        new ConfigColumnMeta(4, "grade", "int"),
        new ConfigColumnMeta(5, "sequence", "int"),
        new ConfigColumnMeta(6, "pos", "int"),
        new ConfigColumnMeta(7, "suitIds", "string"),
        new ConfigColumnMeta(8, "mainAttrPoolId", "int"),
        new ConfigColumnMeta(9, "subAttrPoolId", "int"),
        new ConfigColumnMeta(10, "subAttrUpgradePoolId", "int"),
        new ConfigColumnMeta(11, "upgradeType", "int"),
        new ConfigColumnMeta(12, "decomposeGold", "int"),
        new ConfigColumnMeta(13, "decomposeDG", "int"),
        new ConfigColumnMeta(14, "quality", "int"),
        new ConfigColumnMeta(15, "lightAttrPoolId", "int"));
  }
}
