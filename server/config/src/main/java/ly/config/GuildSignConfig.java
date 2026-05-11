package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuildSignConfig {
  /**状态编号*/
  public final int id;

  /**社团标志*/
  public final int icon;

  /**社团标志框*/
  public final int iconBg;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuildSignConfig(int id, int icon, int iconBg) {
    this.id = id;
    this.icon = icon;
    this.iconBg = iconBg;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
