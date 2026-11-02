package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SupportTeamAttrConfigCheckerBase extends AbstractConfigChecker<SupportTeamAttrConfig> {
  @Override
  public String getConfigFileName() {
    return "supportTeamAttr.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "advance", "int"),
        new ConfigColumnMeta(2, "nextId", "int"),
        new ConfigColumnMeta(3, "description", "string"),
        new ConfigColumnMeta(4, "attrType", "string"));
  }
}
