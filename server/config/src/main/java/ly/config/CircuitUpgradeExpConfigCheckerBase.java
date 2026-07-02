package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CircuitUpgradeExpConfigCheckerBase extends AbstractConfigChecker<CircuitUpgradeExpConfig> {
  @Override
  public String getConfigFileName() {
    return "circuitUpgradeExp.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "upgradeType", "INT"),
        new ConfigColumnMeta(1, "level", "INT"),
        new ConfigColumnMeta(2, "exp", "INT"),
        new ConfigColumnMeta(3, "eatExp", "INT"),
        new ConfigColumnMeta(4, "consumeGold", "INT"),
        new ConfigColumnMeta(5, "newSubAttrProb", "INT"),
        new ConfigColumnMeta(6, "upgrageGold", "INT"));
  }
}
