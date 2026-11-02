package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ManualPoolConfigCheckerBase extends AbstractConfigChecker<ManualPoolConfig> {
  @Override
  public String getConfigFileName() {
    return "manualPool.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "level", "string"),
        new ConfigColumnMeta(2, "regularPoolDaily", "string"),
        new ConfigColumnMeta(3, "randomNumDaily", "string"),
        new ConfigColumnMeta(4, "poolGroupDaily", "string"),
        new ConfigColumnMeta(5, "regularPoolWeek", "string"),
        new ConfigColumnMeta(6, "randomNumWeek", "string"),
        new ConfigColumnMeta(7, "poolGroupWeek", "string"));
  }
}
