package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SquareLineConfigCheckerBase extends AbstractConfigChecker<SquareLineConfig> {
  @Override
  public String getConfigFileName() {
    return "squareLine.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "pos", "string"),
        new ConfigColumnMeta(2, "direction", "int"),
        new ConfigColumnMeta(3, "speed", "int"),
        new ConfigColumnMeta(4, "interval", "int"),
        new ConfigColumnMeta(5, "shift", "string"),
        new ConfigColumnMeta(6, "square", "string"),
        new ConfigColumnMeta(7, "effectPool", "string"));
  }
}
