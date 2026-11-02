package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RechargeShopConfigCheckerBase extends AbstractConfigChecker<RechargeShopConfig> {
  @Override
  public String getConfigFileName() {
    return "RechargeShop.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "name", "string"),
        new ConfigColumnMeta(2, "bugFDesc", "string"),
        new ConfigColumnMeta(3, "bugDesc", "string"),
        new ConfigColumnMeta(4, "bugType", "int"),
        new ConfigColumnMeta(5, "levelUpperLimit", "int"),
        new ConfigColumnMeta(6, "drop", "int"),
        new ConfigColumnMeta(7, "dropShow", "int"),
        new ConfigColumnMeta(8, "reward", "int"),
        new ConfigColumnMeta(9, "give", "string"),
        new ConfigColumnMeta(10, "priceType", "int"),
        new ConfigColumnMeta(11, "price", "int"),
        new ConfigColumnMeta(12, "priceShow", "int"),
        new ConfigColumnMeta(13, "PrePriceShow", "int"),
        new ConfigColumnMeta(14, "page", "string"),
        new ConfigColumnMeta(15, "page1", "int"),
        new ConfigColumnMeta(16, "priority", "int"),
        new ConfigColumnMeta(17, "missionID", "int"),
        new ConfigColumnMeta(18, "cardPower", "int"),
        new ConfigColumnMeta(19, "limtType", "int"),
        new ConfigColumnMeta(20, "limt", "int"),
        new ConfigColumnMeta(21, "icon", "string"),
        new ConfigColumnMeta(22, "duration", "int"),
        new ConfigColumnMeta(23, "closeTime", "string"),
        new ConfigColumnMeta(24, "giveCloseTime", "int"),
        new ConfigColumnMeta(25, "mail", "int"),
        new ConfigColumnMeta(26, "activityId", "int"),
        new ConfigColumnMeta(27, "buySupermarket", "int"));
  }
}
