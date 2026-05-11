package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CommodityInfo2ConfigCheckerBase extends AbstractConfigChecker<CommodityInfo2Config> {
  @Override
  public String getConfigFileName() {
    return "commodityInfo2.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "typePara", "STRING"),
        new ConfigColumnMeta(3, "grade", "INT"),
        new ConfigColumnMeta(4, "school", "INT"),
        new ConfigColumnMeta(5, "group", "INT"),
        new ConfigColumnMeta(6, "sequence", "INT"),
        new ConfigColumnMeta(7, "name", "STRING"),
        new ConfigColumnMeta(8, "des", "STRING"),
        new ConfigColumnMeta(9, "icon", "INT"),
        new ConfigColumnMeta(10, "itemList", "STRING"),
        new ConfigColumnMeta(11, "limitType", "INT"),
        new ConfigColumnMeta(12, "limitPara", "INT"),
        new ConfigColumnMeta(13, "levelLowerLimit", "INT"),
        new ConfigColumnMeta(14, "levelUpperLimit", "INT"),
        new ConfigColumnMeta(15, "moneyType", "INT"),
        new ConfigColumnMeta(16, "price", "INT"),
        new ConfigColumnMeta(17, "priceShow", "INT"),
        new ConfigColumnMeta(18, "PrePriceShow", "INT"),
        new ConfigColumnMeta(19, "priceStepValue", "INT"),
        new ConfigColumnMeta(20, "timeType", "INT"),
        new ConfigColumnMeta(21, "activityID", "INT"),
        new ConfigColumnMeta(22, "startTime", "STRING"),
        new ConfigColumnMeta(23, "endTime", "STRING"),
        new ConfigColumnMeta(24, "specialEndTime", "STRING"),
        new ConfigColumnMeta(25, "isShowTime", "INT"),
        new ConfigColumnMeta(26, "sloganType", "STRING"),
        new ConfigColumnMeta(27, "startTimeTips", "INT"),
        new ConfigColumnMeta(28, "startTimeWord", "STRING"),
        new ConfigColumnMeta(29, "endTimeTips", "INT"),
        new ConfigColumnMeta(30, "groupId", "INT"),
        new ConfigColumnMeta(31, "batch", "INT"),
        new ConfigColumnMeta(32, "rechargeShopId", "INT"),
        new ConfigColumnMeta(33, "rechargeShopId1", "INT"),
        new ConfigColumnMeta(34, "tabshow", "INT"),
        new ConfigColumnMeta(35, "herocondition", "INT"),
        new ConfigColumnMeta(36, "rechargecondition", "INT"),
        new ConfigColumnMeta(37, "firstCharge", "STRING"),
        new ConfigColumnMeta(38, "followupCharge", "STRING"),
        new ConfigColumnMeta(39, "autoOpenGift", "INT"),
        new ConfigColumnMeta(40, "extraShowItemId", "INT"),
        new ConfigColumnMeta(41, "OpenServiceActivity", "INT"));
  }
}
