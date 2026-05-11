package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CircuitAttrPoolConfigCheckerBase extends AbstractConfigChecker<CircuitAttrPoolConfig> {
  @Override
  public String getConfigFileName() {
    return "circuitAttrPool.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "des", "STRING"),
        new ConfigColumnMeta(2, "attrList", "STRING"),
        new ConfigColumnMeta(3, "attrPro", "STRING"),
        new ConfigColumnMeta(4, "attrRelativePro", "STRING"));
  }
}
