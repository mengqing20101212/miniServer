package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CircuitAttrPoolConfigCheckerBase extends AbstractConfigChecker<CircuitAttrPoolConfig> {
  @Override
  public String getConfigFileName() {
    return "circuitAttrPool.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "des", "string"),
        new ConfigColumnMeta(2, "attrList", "string"),
        new ConfigColumnMeta(3, "attrPro", "string"),
        new ConfigColumnMeta(4, "attrRelativePro", "string"));
  }
}
