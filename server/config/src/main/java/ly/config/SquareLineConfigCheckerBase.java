package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SquareLineConfigCheckerBase extends AbstractConfigChecker<SquareLineConfig> {
  @Override
  public String getConfigFileName() {
    return "squareLine.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "pos", "STRING"),
        new ConfigColumnMeta(2, "direction", "INT"),
        new ConfigColumnMeta(3, "speed", "INT"),
        new ConfigColumnMeta(4, "interval", "INT"),
        new ConfigColumnMeta(5, "shift", "STRING"),
        new ConfigColumnMeta(6, "square", "STRING"),
        new ConfigColumnMeta(7, "effectPool", "STRING"));
  }
}
