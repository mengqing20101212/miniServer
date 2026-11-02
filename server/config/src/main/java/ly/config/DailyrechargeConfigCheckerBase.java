package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DailyrechargeConfigCheckerBase extends AbstractConfigChecker<DailyrechargeConfig> {
  @Override
  public String getConfigFileName() {
    return "dailyrecharge.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "stage", "int"),
        new ConfigColumnMeta(2, "Days", "int"),
        new ConfigColumnMeta(3, "rewardShow", "string"),
        new ConfigColumnMeta(4, "drop", "int"),
        new ConfigColumnMeta(5, "rechargShow", "int"),
        new ConfigColumnMeta(6, "rewardsfree", "int"),
        new ConfigColumnMeta(7, "rewardsfinally", "int"),
        new ConfigColumnMeta(8, "finallydrop", "int"),
        new ConfigColumnMeta(9, "RechargeShop", "int"));
  }
}
