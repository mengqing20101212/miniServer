package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Pre_loadConfigCheckerBase extends AbstractConfigChecker<Pre_loadConfig> {
  @Override
  public String getConfigFileName() {
    return "pre_load.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "desc", "string"),
        new ConfigColumnMeta(2, "resid", "int"),
        new ConfigColumnMeta(3, "restype", "int"),
        new ConfigColumnMeta(4, "type", "int"),
        new ConfigColumnMeta(5, "holdstate", "int"),
        new ConfigColumnMeta(6, "param", "string"));
  }
}
