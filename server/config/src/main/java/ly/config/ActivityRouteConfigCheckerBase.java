package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityRouteConfigCheckerBase extends AbstractConfigChecker<ActivityRouteConfig> {
  @Override
  public String getConfigFileName() {
    return "activityRoute.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "drop", "INT"),
        new ConfigColumnMeta(2, "rewardShow", "STRING"),
        new ConfigColumnMeta(3, "score", "INT"),
        new ConfigColumnMeta(4, "array", "INT"),
        new ConfigColumnMeta(5, "row", "INT"),
        new ConfigColumnMeta(6, "front", "STRING"),
        new ConfigColumnMeta(7, "payGift", "INT"),
        new ConfigColumnMeta(8, "payGiftShow", "STRING"),
        new ConfigColumnMeta(9, "rechargeId", "INT"),
        new ConfigColumnMeta(10, "isCrossNode", "INT"),
        new ConfigColumnMeta(11, "drop2", "INT"),
        new ConfigColumnMeta(12, "rewardShow2", "STRING"),
        new ConfigColumnMeta(13, "line", "STRING"),
        new ConfigColumnMeta(14, "circle", "STRING"));
  }
}
