package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SupportTeamAttrConfigCheckerBase extends AbstractConfigChecker<SupportTeamAttrConfig> {
  @Override
  public String getConfigFileName() {
    return "supportTeamAttr.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "advance", "INT"),
        new ConfigColumnMeta(2, "nextId", "INT"),
        new ConfigColumnMeta(3, "description", "STRING"),
        new ConfigColumnMeta(4, "attrType", "STRING"));
  }
}
