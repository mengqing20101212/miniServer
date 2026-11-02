package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class TrialawardConfigCheckerBase extends AbstractConfigChecker<TrialawardConfig> {
  @Override
  public String getConfigFileName() {
    return "trialaward.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "desc", "string"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "condition", "int"),
        new ConfigColumnMeta(3, "conditiondesc", "int"),
        new ConfigColumnMeta(4, "award", "int"));
  }
}
