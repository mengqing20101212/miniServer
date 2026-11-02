package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DailyDivinationConfigCheckerBase extends AbstractConfigChecker<DailyDivinationConfig> {
  @Override
  public String getConfigFileName() {
    return "dailyDivination.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "times", "int"),
        new ConfigColumnMeta(2, "reward", "int"),
        new ConfigColumnMeta(3, "num", "int"));
  }
}
