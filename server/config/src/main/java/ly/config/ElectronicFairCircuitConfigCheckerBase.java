package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ElectronicFairCircuitConfigCheckerBase extends AbstractConfigChecker<ElectronicFairCircuitConfig> {
  @Override
  public String getConfigFileName() {
    return "electronicFairCircuit.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "circuitName", "string"),
        new ConfigColumnMeta(2, "circuit1", "int"),
        new ConfigColumnMeta(3, "circuitAttr1", "string"),
        new ConfigColumnMeta(4, "circuit2", "int"),
        new ConfigColumnMeta(5, "circuitAttr2", "string"),
        new ConfigColumnMeta(6, "circuit3", "int"),
        new ConfigColumnMeta(7, "circuitAttr3", "string"),
        new ConfigColumnMeta(8, "circuit4", "int"),
        new ConfigColumnMeta(9, "circuitAttr4", "string"),
        new ConfigColumnMeta(10, "circuit5", "int"),
        new ConfigColumnMeta(11, "circuitAttr5", "string"),
        new ConfigColumnMeta(12, "circuit6", "int"),
        new ConfigColumnMeta(13, "circuitAttr6", "string"),
        new ConfigColumnMeta(14, "circuit7", "int"),
        new ConfigColumnMeta(15, "circuitAttr7", "string"));
  }
}
