package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityrouletteConfigCheckerBase extends AbstractConfigChecker<ActivityrouletteConfig> {
  @Override
  public String getConfigFileName() {
    return "activityroulette.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "scheDuling", "int"),
        new ConfigColumnMeta(2, "prizeId", "int"),
        new ConfigColumnMeta(3, "luckyPrize", "int"),
        new ConfigColumnMeta(4, "rafflePoints", "int"),
        new ConfigColumnMeta(5, "minimumPoints", "int"),
        new ConfigColumnMeta(6, "consumableProps", "int"),
        new ConfigColumnMeta(7, "consumptionQuantity", "int"),
        new ConfigColumnMeta(8, "luckValueCap", "int"),
        new ConfigColumnMeta(9, "resetLuck", "int"),
        new ConfigColumnMeta(10, "getLucky", "int"),
        new ConfigColumnMeta(11, "lotteryGuarantee", "int"),
        new ConfigColumnMeta(12, "rewardShow", "string"),
        new ConfigColumnMeta(13, "drop", "int"),
        new ConfigColumnMeta(14, "numberAwards", "int"),
        new ConfigColumnMeta(15, "picture", "string"));
  }
}
