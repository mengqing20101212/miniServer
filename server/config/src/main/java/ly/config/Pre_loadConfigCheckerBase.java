package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Pre_loadConfigCheckerBase extends AbstractConfigChecker<Pre_loadConfig> {
  @Override
  public String getConfigFileName() {
    return "pre_load.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "desc", "STRING"),
        new ConfigColumnMeta(2, "resid", "INT"),
        new ConfigColumnMeta(3, "restype", "INT"),
        new ConfigColumnMeta(4, "type", "INT"),
        new ConfigColumnMeta(5, "holdstate", "INT"),
        new ConfigColumnMeta(6, "param", "STRING"));
  }
}
