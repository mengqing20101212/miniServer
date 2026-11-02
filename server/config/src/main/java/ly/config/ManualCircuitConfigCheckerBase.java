package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ManualCircuitConfigCheckerBase extends AbstractConfigChecker<ManualCircuitConfig> {
  @Override
  public String getConfigFileName() {
    return "manualCircuit.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "taskId", "int"),
        new ConfigColumnMeta(2, "front", "string"),
        new ConfigColumnMeta(3, "oriPos", "list<int>"),
        new ConfigColumnMeta(4, "type", "list<int>"),
        new ConfigColumnMeta(5, "redirectionIdDes", "string"),
        new ConfigColumnMeta(6, "redirectionId", "int"));
  }
}
