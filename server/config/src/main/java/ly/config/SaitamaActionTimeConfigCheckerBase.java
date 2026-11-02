package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SaitamaActionTimeConfigCheckerBase extends AbstractConfigChecker<SaitamaActionTimeConfig> {
  @Override
  public String getConfigFileName() {
    return "saitamaActionTime.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "group", "int"),
        new ConfigColumnMeta(2, "actionPool1", "string"),
        new ConfigColumnMeta(3, "actionPool2", "string"),
        new ConfigColumnMeta(4, "time", "string"));
  }
}
