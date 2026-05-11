package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ManualLevelConfigCheckerBase extends AbstractConfigChecker<ManualLevelConfig> {
  @Override
  public String getConfigFileName() {
    return "manualLevel.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "nextLv", "INT"),
        new ConfigColumnMeta(2, "dropShow", "STRING"),
        new ConfigColumnMeta(3, "drop", "INT"),
        new ConfigColumnMeta(4, "sign", "INT"));
  }
}
