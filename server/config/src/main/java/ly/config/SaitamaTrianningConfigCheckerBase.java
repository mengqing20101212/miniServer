package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SaitamaTrianningConfigCheckerBase extends AbstractConfigChecker<SaitamaTrianningConfig> {
  @Override
  public String getConfigFileName() {
    return "saitamaTrianning.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "icon", "INT"),
        new ConfigColumnMeta(3, "openTime", "INT"),
        new ConfigColumnMeta(4, "reward", "STRING"));
  }
}
