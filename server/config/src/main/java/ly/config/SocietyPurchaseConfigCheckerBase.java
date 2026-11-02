package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SocietyPurchaseConfigCheckerBase extends AbstractConfigChecker<SocietyPurchaseConfig> {
  @Override
  public String getConfigFileName() {
    return "societyPurchase.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "phase", "int"),
        new ConfigColumnMeta(2, "sequence", "int"),
        new ConfigColumnMeta(3, "minLevel", "int"),
        new ConfigColumnMeta(4, "maxLevel", "int"),
        new ConfigColumnMeta(5, "isRare", "int"),
        new ConfigColumnMeta(6, "demandProps", "int"),
        new ConfigColumnMeta(7, "beizhu1", "string"),
        new ConfigColumnMeta(8, "demandNum", "int"),
        new ConfigColumnMeta(9, "eachReward", "string"),
        new ConfigColumnMeta(10, "additionalReward", "string"),
        new ConfigColumnMeta(11, "weights", "int"));
  }
}
