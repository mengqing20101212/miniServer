package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitysupplyConfigCheckerBase extends AbstractConfigChecker<ActivitysupplyConfig> {
  @Override
  public String getConfigFileName() {
    return "activitysupply.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "openType", "int"),
        new ConfigColumnMeta(2, "scheDuling", "int"),
        new ConfigColumnMeta(3, "shopId", "int"),
        new ConfigColumnMeta(4, "drop", "int"),
        new ConfigColumnMeta(5, "rechargShow", "int"),
        new ConfigColumnMeta(6, "sloganName", "string"),
        new ConfigColumnMeta(7, "sloganBg", "int"),
        new ConfigColumnMeta(8, "limitPara", "int"),
        new ConfigColumnMeta(9, "rechargeShopId", "int"),
        new ConfigColumnMeta(10, "refreshTime", "int"),
        new ConfigColumnMeta(11, "moneyType", "int"),
        new ConfigColumnMeta(12, "quantity", "int"));
  }
}
