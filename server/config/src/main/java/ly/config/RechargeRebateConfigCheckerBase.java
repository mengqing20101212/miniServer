package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RechargeRebateConfigCheckerBase extends AbstractConfigChecker<RechargeRebateConfig> {
  @Override
  public String getConfigFileName() {
    return "RechargeRebate.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "openId", "string"),
        new ConfigColumnMeta(2, "testRecharge_1", "int"),
        new ConfigColumnMeta(3, "testRecharge_2", "int"),
        new ConfigColumnMeta(4, "testRecharge_3", "int"),
        new ConfigColumnMeta(5, "testRecharge_4", "int"),
        new ConfigColumnMeta(6, "testRecharge_5", "int"),
        new ConfigColumnMeta(7, "testRecharge_6", "int"),
        new ConfigColumnMeta(8, "testRecharge_7", "int"));
  }
}
