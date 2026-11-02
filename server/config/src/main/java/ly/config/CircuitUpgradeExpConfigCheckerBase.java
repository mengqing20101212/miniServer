package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CircuitUpgradeExpConfigCheckerBase extends AbstractConfigChecker<CircuitUpgradeExpConfig> {
  @Override
  public String getConfigFileName() {
    return "circuitUpgradeExp.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "upgradeType", "int"),
        new ConfigColumnMeta(1, "level", "int"),
        new ConfigColumnMeta(2, "exp", "int"),
        new ConfigColumnMeta(3, "eatExp", "int"),
        new ConfigColumnMeta(4, "consumeGold", "int"),
        new ConfigColumnMeta(5, "newSubAttrProb", "int"),
        new ConfigColumnMeta(6, "upgrageGold", "int"));
  }
}
