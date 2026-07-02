package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityrouletteConfigCheckerBase extends AbstractConfigChecker<ActivityrouletteConfig> {
  @Override
  public String getConfigFileName() {
    return "activityroulette.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "scheDuling", "INT"),
        new ConfigColumnMeta(2, "prizeId", "INT"),
        new ConfigColumnMeta(3, "luckyPrize", "INT"),
        new ConfigColumnMeta(4, "rafflePoints", "INT"),
        new ConfigColumnMeta(5, "minimumPoints", "INT"),
        new ConfigColumnMeta(6, "consumableProps", "INT"),
        new ConfigColumnMeta(7, "consumptionQuantity", "INT"),
        new ConfigColumnMeta(8, "luckValueCap", "INT"),
        new ConfigColumnMeta(9, "resetLuck", "INT"),
        new ConfigColumnMeta(10, "getLucky", "INT"),
        new ConfigColumnMeta(11, "lotteryGuarantee", "INT"),
        new ConfigColumnMeta(12, "rewardShow", "STRING"),
        new ConfigColumnMeta(13, "drop", "INT"),
        new ConfigColumnMeta(14, "numberAwards", "INT"),
        new ConfigColumnMeta(15, "picture", "STRING"));
  }
}
