package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ManualBeginnerConfigCheckerBase extends AbstractConfigChecker<ManualBeginnerConfig> {
  @Override
  public String getConfigFileName() {
    return "manualBeginner.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "nextId", "INT"),
        new ConfigColumnMeta(2, "showWord", "STRING"),
        new ConfigColumnMeta(3, "frontId", "INT"),
        new ConfigColumnMeta(4, "fuzhu", "STRING"));
  }
}
