package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitylimitedConfigCheckerBase extends AbstractConfigChecker<ActivitylimitedConfig> {
  @Override
  public String getConfigFileName() {
    return "activitylimited.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "sidebar", "string"),
        new ConfigColumnMeta(2, "presetsId", "int"),
        new ConfigColumnMeta(3, "picture1", "string"),
        new ConfigColumnMeta(4, "picture2", "string"),
        new ConfigColumnMeta(5, "mustBuy1", "string"),
        new ConfigColumnMeta(6, "mustBuy2", "string"),
        new ConfigColumnMeta(7, "discountResID", "string"),
        new ConfigColumnMeta(8, "titleResID", "int"));
  }
}
