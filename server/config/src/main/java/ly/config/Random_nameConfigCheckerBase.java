package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Random_nameConfigCheckerBase extends AbstractConfigChecker<Random_nameConfig> {
  @Override
  public String getConfigFileName() {
    return "random_name.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "name2", "STRING"));
  }
}
