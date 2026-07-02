package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitylimitedConfigCheckerBase extends AbstractConfigChecker<ActivitylimitedConfig> {
  @Override
  public String getConfigFileName() {
    return "activitylimited.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "sidebar", "STRING"),
        new ConfigColumnMeta(2, "presetsId", "INT"),
        new ConfigColumnMeta(3, "picture1", "STRING"),
        new ConfigColumnMeta(4, "picture2", "STRING"),
        new ConfigColumnMeta(5, "mustBuy1", "STRING"),
        new ConfigColumnMeta(6, "mustBuy2", "STRING"),
        new ConfigColumnMeta(7, "discountResID", "STRING"),
        new ConfigColumnMeta(8, "titleResID", "INT"));
  }
}
