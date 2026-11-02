package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivityRouteConfigCheckerBase extends AbstractConfigChecker<ActivityRouteConfig> {
  @Override
  public String getConfigFileName() {
    return "activityRoute.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "drop", "int"),
        new ConfigColumnMeta(2, "rewardShow", "string"),
        new ConfigColumnMeta(3, "score", "int"),
        new ConfigColumnMeta(4, "array", "int"),
        new ConfigColumnMeta(5, "row", "int"),
        new ConfigColumnMeta(6, "front", "string"),
        new ConfigColumnMeta(7, "payGift", "int"),
        new ConfigColumnMeta(8, "payGiftShow", "string"),
        new ConfigColumnMeta(9, "rechargeId", "int"),
        new ConfigColumnMeta(10, "isCrossNode", "int"),
        new ConfigColumnMeta(11, "drop2", "int"),
        new ConfigColumnMeta(12, "rewardShow2", "string"),
        new ConfigColumnMeta(13, "line", "string"),
        new ConfigColumnMeta(14, "circle", "string"));
  }
}
