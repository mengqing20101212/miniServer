package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CircuitGradeConfigCheckerBase extends AbstractConfigChecker<CircuitGradeConfig> {
  @Override
  public String getConfigFileName() {
    return "circuitGrade.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "description", "string"),
        new ConfigColumnMeta(2, "quality", "int"),
        new ConfigColumnMeta(3, "lv1", "int"),
        new ConfigColumnMeta(4, "lv2", "int"),
        new ConfigColumnMeta(5, "lv3", "int"),
        new ConfigColumnMeta(6, "lv4", "int"),
        new ConfigColumnMeta(7, "lv5", "int"),
        new ConfigColumnMeta(8, "lv6", "int"),
        new ConfigColumnMeta(9, "lv7", "int"),
        new ConfigColumnMeta(10, "lv8", "int"),
        new ConfigColumnMeta(11, "lv9", "int"),
        new ConfigColumnMeta(12, "lv10", "int"),
        new ConfigColumnMeta(13, "lv11", "int"),
        new ConfigColumnMeta(14, "lv12", "int"),
        new ConfigColumnMeta(15, "lv13", "int"),
        new ConfigColumnMeta(16, "lv14", "int"),
        new ConfigColumnMeta(17, "lv15", "int"));
  }
}
