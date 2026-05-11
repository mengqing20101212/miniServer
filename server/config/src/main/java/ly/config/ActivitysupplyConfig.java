package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivitysupplyConfig {
  /**商品ID*/
  public final int id;

  /**开启类型*/
  public final int openType;

  /**活动排期*/
  public final int scheDuling;

  /**商店波次*/
  public final int shopId;

  /**实际掉落*/
  public final int drop;

  /**显示道具*/
  public final int rechargShow;

  /**标语类型名字*/
  public final String sloganName;

  /**标语背景*/
  public final int sloganBg;

  /**限购次数*/
  public final int limitPara;

  /**商品ID*/
  public final int rechargeShopId;

  /**刷新时间*/
  public final int refreshTime;

  /**货币种类*/
  public final int moneyType;

  /**货币数量*/
  public final int quantity;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivitysupplyConfig(int id, int openType, int scheDuling, int shopId, int drop, int rechargShow, String sloganName, int sloganBg, int limitPara, int rechargeShopId, int refreshTime, int moneyType, int quantity) {
    this.id = id;
    this.openType = openType;
    this.scheDuling = scheDuling;
    this.shopId = shopId;
    this.drop = drop;
    this.rechargShow = rechargShow;
    this.sloganName = sloganName;
    this.sloganBg = sloganBg;
    this.limitPara = limitPara;
    this.rechargeShopId = rechargeShopId;
    this.refreshTime = refreshTime;
    this.moneyType = moneyType;
    this.quantity = quantity;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
