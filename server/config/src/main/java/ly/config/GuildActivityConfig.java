package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuildActivityConfig {
  /**状态编号*/
  public final int id;

  /**  null*/
  public final String name;

  /**玩法描述*/
  public final String desc;

  /**对应图片*/
  public final int icon;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuildActivityConfig(int id, String name, String desc, int icon) {
    this.id = id;
    this.name = name;
    this.desc = desc;
    this.icon = icon;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
