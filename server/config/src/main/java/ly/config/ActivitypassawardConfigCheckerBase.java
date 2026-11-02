package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class ActivitypassawardConfigCheckerBase extends AbstractConfigChecker<ActivitypassawardConfig> {
  @Override
  public String getConfigFileName() {
    return "activitypassaward.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "scheDuling", "int"),
        new ConfigColumnMeta(2, "level", "int"),
        new ConfigColumnMeta(3, "payLevel", "int"),
        new ConfigColumnMeta(4, "rechargeShopId", "int"),
        new ConfigColumnMeta(5, "score", "int"),
        new ConfigColumnMeta(6, "freeGift", "int"),
        new ConfigColumnMeta(7, "freeGiftShow", "int"),
        new ConfigColumnMeta(8, "payGift", "int"),
        new ConfigColumnMeta(9, "payGiftShow", "int"),
        new ConfigColumnMeta(10, "redirectionId", "int"));
  }
}
