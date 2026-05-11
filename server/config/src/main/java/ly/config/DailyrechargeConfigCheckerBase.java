package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DailyrechargeConfigCheckerBase extends AbstractConfigChecker<DailyrechargeConfig> {
  @Override
  public String getConfigFileName() {
    return "dailyrecharge.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "stage", "INT"),
        new ConfigColumnMeta(2, "Days", "INT"),
        new ConfigColumnMeta(3, "rewardShow", "STRING"),
        new ConfigColumnMeta(4, "drop", "INT"),
        new ConfigColumnMeta(5, "rechargShow", "INT"),
        new ConfigColumnMeta(6, "rewardsfree", "INT"),
        new ConfigColumnMeta(7, "rewardsfinally", "INT"),
        new ConfigColumnMeta(8, "finallydrop", "INT"),
        new ConfigColumnMeta(9, "RechargeShop", "INT"));
  }
}
