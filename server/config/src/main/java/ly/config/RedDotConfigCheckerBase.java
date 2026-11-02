package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RedDotConfigCheckerBase extends AbstractConfigChecker<RedDotConfig> {
  @Override
  public String getConfigFileName() {
    return "redDot.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "SWITCH", "int"),
        new ConfigColumnMeta(2, "number", "int"),
        new ConfigColumnMeta(3, "type", "int"),
        new ConfigColumnMeta(4, "father", "int"),
        new ConfigColumnMeta(5, "subclass", "string"),
        new ConfigColumnMeta(6, "des", "string"));
  }
}
