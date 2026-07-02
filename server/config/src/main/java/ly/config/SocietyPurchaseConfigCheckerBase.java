package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SocietyPurchaseConfigCheckerBase extends AbstractConfigChecker<SocietyPurchaseConfig> {
  @Override
  public String getConfigFileName() {
    return "societyPurchase.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "phase", "INT"),
        new ConfigColumnMeta(2, "sequence", "INT"),
        new ConfigColumnMeta(3, "minLevel", "INT"),
        new ConfigColumnMeta(4, "maxLevel", "INT"),
        new ConfigColumnMeta(5, "isRare", "INT"),
        new ConfigColumnMeta(6, "demandProps", "INT"),
        new ConfigColumnMeta(7, "beizhu1", "STRING"),
        new ConfigColumnMeta(8, "demandNum", "INT"),
        new ConfigColumnMeta(9, "eachReward", "STRING"),
        new ConfigColumnMeta(10, "additionalReward", "STRING"),
        new ConfigColumnMeta(11, "weights", "INT"));
  }
}
