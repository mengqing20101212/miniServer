package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SupportTeamHeroAttrConfigCheckerBase extends AbstractConfigChecker<SupportTeamHeroAttrConfig> {
  @Override
  public String getConfigFileName() {
    return "supportTeamHeroAttr.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "attrClass", "int"),
        new ConfigColumnMeta(2, "attrLevel", "int"),
        new ConfigColumnMeta(3, "upCostItemNum", "int"),
        new ConfigColumnMeta(4, "attrType", "string"),
        new ConfigColumnMeta(5, "attrName", "string"),
        new ConfigColumnMeta(6, "unlcok", "int"),
        new ConfigColumnMeta(7, "icon", "string"));
  }
}
