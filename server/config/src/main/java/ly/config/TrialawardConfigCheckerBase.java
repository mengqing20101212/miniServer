package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class TrialawardConfigCheckerBase extends AbstractConfigChecker<TrialawardConfig> {
  @Override
  public String getConfigFileName() {
    return "trialaward.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "desc", "STRING"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "condition", "INT"),
        new ConfigColumnMeta(3, "conditiondesc", "INT"),
        new ConfigColumnMeta(4, "award", "INT"));
  }
}
