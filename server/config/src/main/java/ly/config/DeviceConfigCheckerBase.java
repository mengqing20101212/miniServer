package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class DeviceConfigCheckerBase extends AbstractConfigChecker<DeviceConfig> {
  @Override
  public String getConfigFileName() {
    return "device.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "model", "string"),
        new ConfigColumnMeta(2, "desc", "string"),
        new ConfigColumnMeta(3, "qualityLevel", "int"),
        new ConfigColumnMeta(4, "isNotch", "bool"),
        new ConfigColumnMeta(5, "notchHeight", "int"));
  }
}
