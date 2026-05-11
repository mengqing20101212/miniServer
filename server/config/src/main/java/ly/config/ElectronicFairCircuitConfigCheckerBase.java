package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ElectronicFairCircuitConfigCheckerBase extends AbstractConfigChecker<ElectronicFairCircuitConfig> {
  @Override
  public String getConfigFileName() {
    return "electronicFairCircuit.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "circuitName", "STRING"),
        new ConfigColumnMeta(2, "circuit1", "INT"),
        new ConfigColumnMeta(3, "circuitAttr1", "STRING"),
        new ConfigColumnMeta(4, "circuit2", "INT"),
        new ConfigColumnMeta(5, "circuitAttr2", "STRING"),
        new ConfigColumnMeta(6, "circuit3", "INT"),
        new ConfigColumnMeta(7, "circuitAttr3", "STRING"),
        new ConfigColumnMeta(8, "circuit4", "INT"),
        new ConfigColumnMeta(9, "circuitAttr4", "STRING"),
        new ConfigColumnMeta(10, "circuit5", "INT"),
        new ConfigColumnMeta(11, "circuitAttr5", "STRING"),
        new ConfigColumnMeta(12, "circuit6", "INT"),
        new ConfigColumnMeta(13, "circuitAttr6", "STRING"),
        new ConfigColumnMeta(14, "circuit7", "INT"),
        new ConfigColumnMeta(15, "circuitAttr7", "STRING"));
  }
}
