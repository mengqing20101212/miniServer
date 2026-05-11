package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ShopFreeDicConfig {
  /**ID*/
  public final int id;

  /**显示二级商店id*/
  public final int showShopId;

  /**真实二级商店id*/
  public final int trueShopId;

  /**显示商品id*/
  public final int showCommodityId;

  /**真实商品id*/
  public final int trueCommodityId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ShopFreeDicConfig(int id, int showShopId, int trueShopId, int showCommodityId, int trueCommodityId) {
    this.id = id;
    this.showShopId = showShopId;
    this.trueShopId = trueShopId;
    this.showCommodityId = showCommodityId;
    this.trueCommodityId = trueCommodityId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
