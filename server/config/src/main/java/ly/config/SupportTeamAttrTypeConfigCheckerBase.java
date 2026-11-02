package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SupportTeamAttrTypeConfigCheckerBase extends AbstractConfigChecker<SupportTeamAttrTypeConfig> {
  @Override
  public String getConfigFileName() {
    return "supportTeamAttrType.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "attrType", "int"),
        new ConfigColumnMeta(1, "function", "string"),
        new ConfigColumnMeta(2, "des", "string"),
        new ConfigColumnMeta(3, "icon", "int"),
        new ConfigColumnMeta(4, "isPercent", "int"));
  }
}
