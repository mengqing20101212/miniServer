package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SaitamaActionTimeConfigCheckerBase extends AbstractConfigChecker<SaitamaActionTimeConfig> {
  @Override
  public String getConfigFileName() {
    return "saitamaActionTime.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "group", "INT"),
        new ConfigColumnMeta(2, "actionPool1", "STRING"),
        new ConfigColumnMeta(3, "actionPool2", "STRING"),
        new ConfigColumnMeta(4, "time", "STRING"));
  }
}
