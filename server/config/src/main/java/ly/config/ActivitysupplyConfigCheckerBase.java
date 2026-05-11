package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitysupplyConfigCheckerBase extends AbstractConfigChecker<ActivitysupplyConfig> {
  @Override
  public String getConfigFileName() {
    return "activitysupply.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "openType", "INT"),
        new ConfigColumnMeta(2, "scheDuling", "INT"),
        new ConfigColumnMeta(3, "shopId", "INT"),
        new ConfigColumnMeta(4, "drop", "INT"),
        new ConfigColumnMeta(5, "rechargShow", "INT"),
        new ConfigColumnMeta(6, "sloganName", "STRING"),
        new ConfigColumnMeta(7, "sloganBg", "INT"),
        new ConfigColumnMeta(8, "limitPara", "INT"),
        new ConfigColumnMeta(9, "rechargeShopId", "INT"),
        new ConfigColumnMeta(10, "refreshTime", "INT"),
        new ConfigColumnMeta(11, "moneyType", "INT"),
        new ConfigColumnMeta(12, "quantity", "INT"));
  }
}
