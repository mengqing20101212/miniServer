package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RedDotConfigCheckerBase extends AbstractConfigChecker<RedDotConfig> {
  @Override
  public String getConfigFileName() {
    return "redDot.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "SWITCH", "INT"),
        new ConfigColumnMeta(2, "number", "INT"),
        new ConfigColumnMeta(3, "type", "INT"),
        new ConfigColumnMeta(4, "father", "INT"),
        new ConfigColumnMeta(5, "subclass", "STRING"),
        new ConfigColumnMeta(6, "des", "STRING"));
  }
}
