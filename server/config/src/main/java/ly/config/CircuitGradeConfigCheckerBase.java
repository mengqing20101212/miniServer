package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CircuitGradeConfigCheckerBase extends AbstractConfigChecker<CircuitGradeConfig> {
  @Override
  public String getConfigFileName() {
    return "circuitGrade.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "description", "STRING"),
        new ConfigColumnMeta(2, "quality", "INT"),
        new ConfigColumnMeta(3, "lv1", "INT"),
        new ConfigColumnMeta(4, "lv2", "INT"),
        new ConfigColumnMeta(5, "lv3", "INT"),
        new ConfigColumnMeta(6, "lv4", "INT"),
        new ConfigColumnMeta(7, "lv5", "INT"),
        new ConfigColumnMeta(8, "lv6", "INT"),
        new ConfigColumnMeta(9, "lv7", "INT"),
        new ConfigColumnMeta(10, "lv8", "INT"),
        new ConfigColumnMeta(11, "lv9", "INT"),
        new ConfigColumnMeta(12, "lv10", "INT"),
        new ConfigColumnMeta(13, "lv11", "INT"),
        new ConfigColumnMeta(14, "lv12", "INT"),
        new ConfigColumnMeta(15, "lv13", "INT"),
        new ConfigColumnMeta(16, "lv14", "INT"),
        new ConfigColumnMeta(17, "lv15", "INT"));
  }
}
