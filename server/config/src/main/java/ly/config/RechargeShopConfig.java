package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class RechargeShopConfig {
  /**编号*/
  public final int id;

  /**商品名称名称*/
  public final String name;

  /**购买说明*/
  public final String bugFDesc;

  /**正常购买说明*/
  public final String bugDesc;

  /**购买物品类型*/
  public final int bugType;

  /**显示等级*/
  public final int levelUpperLimit;

  /**礼包后端*/
  public final int drop;

  /**礼包前端*/
  public final int dropShow;

  /**购买获得彩钻*/
  public final int reward;

  /**购买赠送*/
  public final String give;

  /**价格类型*/
  public final int priceType;

  /**价格1*/
  public final int price;

  /**显示价格*/
  public final int priceShow;

  /**原价*/
  public final int PrePriceShow;

  /**页签*/
  public final String page;

  /**子页签*/
  public final int page1;

  /**展示优先级*/
  public final int priority;

  /**触发条件*/
  public final int missionID;

  /**是否月卡权限*/
  public final int cardPower;

  /**限购类型*/
  public final int limtType;

  /**限购数量*/
  public final int limt;

  /**图标*/
  public final String icon;

  /**持续时间*/
  public final int duration;

  /**下架时间*/
  public final String closeTime;

  /**限时礼包过期时间*/
  public final int giveCloseTime;

  /**邮件ID*/
  public final int mail;

  /**活动ID*/
  public final int activityId;

  /**是否超市购买*/
  public final int buySupermarket;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public RechargeShopConfig(int id, String name, String bugFDesc, String bugDesc, int bugType, int levelUpperLimit, int drop, int dropShow, int reward, String give, int priceType, int price, int priceShow, int PrePriceShow, String page, int page1, int priority, int missionID, int cardPower, int limtType, int limt, String icon, int duration, String closeTime, int giveCloseTime, int mail, int activityId, int buySupermarket) {
    this.id = id;
    this.name = name;
    this.bugFDesc = bugFDesc;
    this.bugDesc = bugDesc;
    this.bugType = bugType;
    this.levelUpperLimit = levelUpperLimit;
    this.drop = drop;
    this.dropShow = dropShow;
    this.reward = reward;
    this.give = give;
    this.priceType = priceType;
    this.price = price;
    this.priceShow = priceShow;
    this.PrePriceShow = PrePriceShow;
    this.page = page;
    this.page1 = page1;
    this.priority = priority;
    this.missionID = missionID;
    this.cardPower = cardPower;
    this.limtType = limtType;
    this.limt = limt;
    this.icon = icon;
    this.duration = duration;
    this.closeTime = closeTime;
    this.giveCloseTime = giveCloseTime;
    this.mail = mail;
    this.activityId = activityId;
    this.buySupermarket = buySupermarket;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
