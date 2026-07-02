package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DeviceConfigCheckerBase extends AbstractConfigChecker<DeviceConfig> {
  @Override
  public String getConfigFileName() {
    return "device.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "model", "STRING"),
        new ConfigColumnMeta(2, "desc", "STRING"),
        new ConfigColumnMeta(3, "qualityLevel", "INT"),
        new ConfigColumnMeta(4, "isNotch", "BOOL"),
        new ConfigColumnMeta(5, "notchHeight", "INT"));
  }
}
