package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RechargeShopConfigCheckerBase extends AbstractConfigChecker<RechargeShopConfig> {
  @Override
  public String getConfigFileName() {
    return "RechargeShop.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "bugFDesc", "STRING"),
        new ConfigColumnMeta(3, "bugDesc", "STRING"),
        new ConfigColumnMeta(4, "bugType", "INT"),
        new ConfigColumnMeta(5, "levelUpperLimit", "INT"),
        new ConfigColumnMeta(6, "drop", "INT"),
        new ConfigColumnMeta(7, "dropShow", "INT"),
        new ConfigColumnMeta(8, "reward", "INT"),
        new ConfigColumnMeta(9, "give", "STRING"),
        new ConfigColumnMeta(10, "priceType", "INT"),
        new ConfigColumnMeta(11, "price", "INT"),
        new ConfigColumnMeta(12, "priceShow", "INT"),
        new ConfigColumnMeta(13, "PrePriceShow", "INT"),
        new ConfigColumnMeta(14, "page", "STRING"),
        new ConfigColumnMeta(15, "page1", "INT"),
        new ConfigColumnMeta(16, "priority", "INT"),
        new ConfigColumnMeta(17, "missionID", "INT"),
        new ConfigColumnMeta(18, "cardPower", "INT"),
        new ConfigColumnMeta(19, "limtType", "INT"),
        new ConfigColumnMeta(20, "limt", "INT"),
        new ConfigColumnMeta(21, "icon", "STRING"),
        new ConfigColumnMeta(22, "duration", "INT"),
        new ConfigColumnMeta(23, "closeTime", "STRING"),
        new ConfigColumnMeta(24, "giveCloseTime", "INT"),
        new ConfigColumnMeta(25, "mail", "INT"),
        new ConfigColumnMeta(26, "activityId", "INT"),
        new ConfigColumnMeta(27, "buySupermarket", "INT"));
  }
}
