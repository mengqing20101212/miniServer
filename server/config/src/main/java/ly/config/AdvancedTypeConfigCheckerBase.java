package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class AdvancedTypeConfigCheckerBase extends AbstractConfigChecker<AdvancedTypeConfig> {
  @Override
  public String getConfigFileName() {
    return "advancedType.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "stageTypeList", "LIST<INT>"),
        new ConfigColumnMeta(2, "activityId", "INT"),
        new ConfigColumnMeta(3, "nameList", "LIST"),
        new ConfigColumnMeta(4, "iconList", "LIST<INT>"),
        new ConfigColumnMeta(5, "outlineList", "LIST<INT>"));
  }
}
