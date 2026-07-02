package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivitylimitedConfig {
  /**限时礼包ID*/
  public final int id;

  /**侧边栏显示配置*/
  public final String sidebar;

  /**预设ID*/
  public final int presetsId;

  /**返利数字*/
  public final String picture1;

  /**总价值数字*/
  public final String picture2;

  /**必买推荐文字1*/
  public final String mustBuy1;

  /**必买推荐文字2*/
  public final String mustBuy2;

  /**折扣标题文字*/
  public final String discountResID;

  /**礼包标题*/
  public final int titleResID;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivitylimitedConfig(int id, String sidebar, int presetsId, String picture1, String picture2, String mustBuy1, String mustBuy2, String discountResID, int titleResID) {
    this.id = id;
    this.sidebar = sidebar;
    this.presetsId = presetsId;
    this.picture1 = picture1;
    this.picture2 = picture2;
    this.mustBuy1 = mustBuy1;
    this.mustBuy2 = mustBuy2;
    this.discountResID = discountResID;
    this.titleResID = titleResID;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
