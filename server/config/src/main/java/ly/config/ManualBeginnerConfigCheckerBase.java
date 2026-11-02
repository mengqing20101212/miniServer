package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ManualBeginnerConfigCheckerBase extends AbstractConfigChecker<ManualBeginnerConfig> {
  @Override
  public String getConfigFileName() {
    return "manualBeginner.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "nextId", "int"),
        new ConfigColumnMeta(2, "showWord", "string"),
        new ConfigColumnMeta(3, "frontId", "int"),
        new ConfigColumnMeta(4, "fuzhu", "string"));
  }
}
