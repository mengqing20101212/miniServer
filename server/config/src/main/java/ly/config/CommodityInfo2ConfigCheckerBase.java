package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class CommodityInfo2ConfigCheckerBase extends AbstractConfigChecker<CommodityInfo2Config> {
  @Override
  public String getConfigFileName() {
    return "commodityInfo2.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "type", "int"),
        new ConfigColumnMeta(2, "typePara", "string"),
        new ConfigColumnMeta(3, "grade", "int"),
        new ConfigColumnMeta(4, "school", "int"),
        new ConfigColumnMeta(5, "group", "int"),
        new ConfigColumnMeta(6, "sequence", "int"),
        new ConfigColumnMeta(7, "name", "string"),
        new ConfigColumnMeta(8, "des", "string"),
        new ConfigColumnMeta(9, "icon", "int"),
        new ConfigColumnMeta(10, "itemList", "string"),
        new ConfigColumnMeta(11, "limitType", "int"),
        new ConfigColumnMeta(12, "limitPara", "int"),
        new ConfigColumnMeta(13, "levelLowerLimit", "int"),
        new ConfigColumnMeta(14, "levelUpperLimit", "int"),
        new ConfigColumnMeta(15, "moneyType", "int"),
        new ConfigColumnMeta(16, "price", "int"),
        new ConfigColumnMeta(17, "priceShow", "int"),
        new ConfigColumnMeta(18, "PrePriceShow", "int"),
        new ConfigColumnMeta(19, "priceStepValue", "int"),
        new ConfigColumnMeta(20, "timeType", "int"),
        new ConfigColumnMeta(21, "activityID", "int"),
        new ConfigColumnMeta(22, "startTime", "string"),
        new ConfigColumnMeta(23, "endTime", "string"),
        new ConfigColumnMeta(24, "specialEndTime", "string"),
        new ConfigColumnMeta(25, "isShowTime", "int"),
        new ConfigColumnMeta(26, "sloganType", "string"),
        new ConfigColumnMeta(27, "startTimeTips", "int"),
        new ConfigColumnMeta(28, "startTimeWord", "string"),
        new ConfigColumnMeta(29, "endTimeTips", "int"),
        new ConfigColumnMeta(30, "groupId", "int"),
        new ConfigColumnMeta(31, "batch", "int"),
        new ConfigColumnMeta(32, "rechargeShopId", "int"),
        new ConfigColumnMeta(33, "rechargeShopId1", "int"),
        new ConfigColumnMeta(34, "tabshow", "int"),
        new ConfigColumnMeta(35, "herocondition", "int"),
        new ConfigColumnMeta(36, "rechargecondition", "int"),
        new ConfigColumnMeta(37, "firstCharge", "string"),
        new ConfigColumnMeta(38, "followupCharge", "string"),
        new ConfigColumnMeta(39, "autoOpenGift", "int"),
        new ConfigColumnMeta(40, "extraShowItemId", "int"),
        new ConfigColumnMeta(41, "OpenServiceActivity", "int"));
  }
}
