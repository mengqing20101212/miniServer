package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuildRefreshShopConfig {
  /**状态编号*/
  public final int id;

  /**物品ID*/
  public final int itemId;

  /**物品数量*/
  public final int itemNum;

  /**权重*/
  public final int weight;

  /**货币类型*/
  public final int currencyType;

  /**价格*/
  public final int price;

  /**是否打折*/
  public final int discount;

  /**社团等级*/
  public final int level;

  /**特殊奖励次数*/
  public final String coloredEggs;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuildRefreshShopConfig(int id, int itemId, int itemNum, int weight, int currencyType, int price, int discount, int level, String coloredEggs) {
    this.id = id;
    this.itemId = itemId;
    this.itemNum = itemNum;
    this.weight = weight;
    this.currencyType = currencyType;
    this.price = price;
    this.discount = discount;
    this.level = level;
    this.coloredEggs = coloredEggs;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
