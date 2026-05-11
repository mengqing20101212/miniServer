package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CircuitInfoConfigCheckerBase extends AbstractConfigChecker<CircuitInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "circuitInfo.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "description", "STRING"),
        new ConfigColumnMeta(3, "mainForward", "INT"),
        new ConfigColumnMeta(4, "route0", "STRING"),
        new ConfigColumnMeta(5, "route1", "STRING"),
        new ConfigColumnMeta(6, "route2", "STRING"),
        new ConfigColumnMeta(7, "route3", "STRING"));
  }
}
