package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ManualCircuitConfigCheckerBase extends AbstractConfigChecker<ManualCircuitConfig> {
  @Override
  public String getConfigFileName() {
    return "manualCircuit.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "taskId", "INT"),
        new ConfigColumnMeta(2, "front", "STRING"),
        new ConfigColumnMeta(3, "oriPos", "LIST<INT>"),
        new ConfigColumnMeta(4, "type", "LIST<INT>"),
        new ConfigColumnMeta(5, "redirectionIdDes", "STRING"),
        new ConfigColumnMeta(6, "redirectionId", "INT"));
  }
}
