package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SaitamaActionConfigCheckerBase extends AbstractConfigChecker<SaitamaActionConfig> {
  @Override
  public String getConfigFileName() {
    return "saitamaAction.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "people", "int"),
        new ConfigColumnMeta(2, "action", "string"),
        new ConfigColumnMeta(3, "beizhu", "string"),
        new ConfigColumnMeta(4, "word", "string"),
        new ConfigColumnMeta(5, "touchWord", "string"),
        new ConfigColumnMeta(6, "coordinate", "string"));
  }
}
