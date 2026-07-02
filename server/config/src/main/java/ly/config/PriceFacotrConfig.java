package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PriceFacotrConfig {
  /**ID*/
  public final int id;

  /**等级*/
  public final String level;

  /**物品ID*/
  public final int itemId;

  /**价格*/
  public final int price;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PriceFacotrConfig(int id, String level, int itemId, int price) {
    this.id = id;
    this.level = level;
    this.itemId = itemId;
    this.price = price;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
