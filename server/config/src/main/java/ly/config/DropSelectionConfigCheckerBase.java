package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DropSelectionConfigCheckerBase extends AbstractConfigChecker<DropSelectionConfig> {
  @Override
  public String getConfigFileName() {
    return "dropSelection.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "nameList", "string"),
        new ConfigColumnMeta(2, "iconList", "string"));
  }
}
