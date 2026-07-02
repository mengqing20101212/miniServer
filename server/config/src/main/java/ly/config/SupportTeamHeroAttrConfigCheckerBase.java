package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SupportTeamHeroAttrConfigCheckerBase extends AbstractConfigChecker<SupportTeamHeroAttrConfig> {
  @Override
  public String getConfigFileName() {
    return "supportTeamHeroAttr.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "attrClass", "INT"),
        new ConfigColumnMeta(2, "attrLevel", "INT"),
        new ConfigColumnMeta(3, "upCostItemNum", "INT"),
        new ConfigColumnMeta(4, "attrType", "STRING"),
        new ConfigColumnMeta(5, "attrName", "STRING"),
        new ConfigColumnMeta(6, "unlcok", "INT"),
        new ConfigColumnMeta(7, "icon", "STRING"));
  }
}
