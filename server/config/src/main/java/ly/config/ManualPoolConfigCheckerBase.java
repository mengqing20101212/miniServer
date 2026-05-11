package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ManualPoolConfigCheckerBase extends AbstractConfigChecker<ManualPoolConfig> {
  @Override
  public String getConfigFileName() {
    return "manualPool.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "level", "STRING"),
        new ConfigColumnMeta(2, "regularPoolDaily", "STRING"),
        new ConfigColumnMeta(3, "randomNumDaily", "STRING"),
        new ConfigColumnMeta(4, "poolGroupDaily", "STRING"),
        new ConfigColumnMeta(5, "regularPoolWeek", "STRING"),
        new ConfigColumnMeta(6, "randomNumWeek", "STRING"),
        new ConfigColumnMeta(7, "poolGroupWeek", "STRING"));
  }
}
