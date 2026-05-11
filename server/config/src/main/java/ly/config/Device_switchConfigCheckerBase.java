package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Device_switchConfigCheckerBase extends AbstractConfigChecker<Device_switchConfig> {
  @Override
  public String getConfigFileName() {
    return "device_switch.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "model", "STRING"),
        new ConfigColumnMeta(1, "grade", "INT"));
  }
}
