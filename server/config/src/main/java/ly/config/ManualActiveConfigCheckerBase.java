package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ManualActiveConfigCheckerBase extends AbstractConfigChecker<ManualActiveConfig> {
  @Override
  public String getConfigFileName() {
    return "manualActive.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "point", "INT"),
        new ConfigColumnMeta(2, "iconId", "INT"),
        new ConfigColumnMeta(3, "reward", "STRING"));
  }
}
