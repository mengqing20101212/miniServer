package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitypassawardConfigCheckerBase extends AbstractConfigChecker<ActivitypassawardConfig> {
  @Override
  public String getConfigFileName() {
    return "activitypassaward.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "scheDuling", "INT"),
        new ConfigColumnMeta(2, "level", "INT"),
        new ConfigColumnMeta(3, "payLevel", "INT"),
        new ConfigColumnMeta(4, "rechargeShopId", "INT"),
        new ConfigColumnMeta(5, "score", "INT"),
        new ConfigColumnMeta(6, "freeGift", "INT"),
        new ConfigColumnMeta(7, "freeGiftShow", "INT"),
        new ConfigColumnMeta(8, "payGift", "INT"),
        new ConfigColumnMeta(9, "payGiftShow", "INT"),
        new ConfigColumnMeta(10, "redirectionId", "INT"));
  }
}
