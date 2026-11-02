package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DeviceConfig {
  public final int id;

  public final String model;

  public final String desc;

  public final int qualityLevel;

  public final boolean isNotch;

  public final int notchHeight;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DeviceConfig(int id, String model, String desc, int qualityLevel, boolean isNotch, int notchHeight) {
    this.id = id;
    this.model = model;
    this.desc = desc;
    this.qualityLevel = qualityLevel;
    this.isNotch = isNotch;
    this.notchHeight = notchHeight;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
