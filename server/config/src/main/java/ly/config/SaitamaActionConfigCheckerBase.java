package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SaitamaActionConfigCheckerBase extends AbstractConfigChecker<SaitamaActionConfig> {
  @Override
  public String getConfigFileName() {
    return "saitamaAction.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "people", "INT"),
        new ConfigColumnMeta(2, "action", "STRING"),
        new ConfigColumnMeta(3, "beizhu", "STRING"),
        new ConfigColumnMeta(4, "word", "STRING"),
        new ConfigColumnMeta(5, "touchWord", "STRING"),
        new ConfigColumnMeta(6, "coordinate", "STRING"));
  }
}
