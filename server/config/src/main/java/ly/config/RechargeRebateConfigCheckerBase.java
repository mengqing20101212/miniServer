package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class RechargeRebateConfigCheckerBase extends AbstractConfigChecker<RechargeRebateConfig> {
  @Override
  public String getConfigFileName() {
    return "RechargeRebate.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "openId", "STRING"),
        new ConfigColumnMeta(2, "testRecharge_1", "INT"),
        new ConfigColumnMeta(3, "testRecharge_2", "INT"),
        new ConfigColumnMeta(4, "testRecharge_3", "INT"),
        new ConfigColumnMeta(5, "testRecharge_4", "INT"),
        new ConfigColumnMeta(6, "testRecharge_5", "INT"),
        new ConfigColumnMeta(7, "testRecharge_6", "INT"),
        new ConfigColumnMeta(8, "testRecharge_7", "INT"));
  }
}
