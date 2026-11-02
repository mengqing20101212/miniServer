package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CircuitInfoConfigCheckerBase extends AbstractConfigChecker<CircuitInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "circuitInfo.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "description", "string"),
        new ConfigColumnMeta(3, "mainForward", "int"),
        new ConfigColumnMeta(4, "route0", "string"),
        new ConfigColumnMeta(5, "route1", "string"),
        new ConfigColumnMeta(6, "route2", "string"),
        new ConfigColumnMeta(7, "route3", "string"));
  }
}
