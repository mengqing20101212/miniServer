package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AdvancedTypeConfigCheckerBase extends AbstractConfigChecker<AdvancedTypeConfig> {
  @Override
  public String getConfigFileName() {
    return "advancedType.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "stageTypeList", "list<int>"),
        new ConfigColumnMeta(2, "activityId", "int"),
        new ConfigColumnMeta(3, "nameList", "string"),
        new ConfigColumnMeta(4, "iconList", "list<int>"),
        new ConfigColumnMeta(5, "outlineList", "list<int>"));
  }
}
