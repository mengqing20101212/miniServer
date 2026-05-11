package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GetGiftConfig {
  /**道具id*/
  public final int id;

  /**道具名字*/
  public final String itemName;

  /**礼包列表*/
  public final String giftList;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GetGiftConfig(int id, String itemName, String giftList) {
    this.id = id;
    this.itemName = itemName;
    this.giftList = giftList;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
