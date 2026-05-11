package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class CommodityInfo2Config {
  /**商品ID*/
  public final int id;

  /**商品类型*/
  public final int type;

  /**商品参数*/
  public final String typePara;

  /**一级商店id*/
  public final int grade;

  /**二级商店id*/
  public final int school;

  /**商品组id*/
  public final int group;

  /**序列id*/
  public final int sequence;

  /**商品名称*/
  public final String name;

  /**商品描述*/
  public final String des;

  /**商品图标*/
  public final int icon;

  /**道具列表*/
  public final String itemList;

  /**限购类型*/
  public final int limitType;

  /**限购参数*/
  public final int limitPara;

  /**等级下限*/
  public final int levelLowerLimit;

  /**等级上限*/
  public final int levelUpperLimit;

  /**货币种类*/
  public final int moneyType;

  /**价格*/
  public final int price;

  /**显示价格*/
  public final int priceShow;

  /**原价*/
  public final int PrePriceShow;

  /**价格累进值*/
  public final int priceStepValue;

  /**时间类型*/
  public final int timeType;

  /**活动ID*/
  public final int activityID;

  /**上架时间*/
  public final String startTime;

  /**下架时间*/
  public final String endTime;

  /**特殊时间*/
  public final String specialEndTime;

  /**是否显示时间*/
  public final int isShowTime;

  /**标语类型*/
  public final String sloganType;

  /**上架提醒*/
  public final int startTimeTips;

  /**上架提醒文字*/
  public final String startTimeWord;

  /**下架提醒*/
  public final int endTimeTips;

  /**商品快捷组id*/
  public final int groupId;

  /**是否批量购买*/
  public final int batch;

  /**商品ID*/
  public final int rechargeShopId;

  /**商品ID*/
  public final int rechargeShopId1;

  /**返利标签*/
  public final int tabshow;

  /**英雄可见性*/
  public final int herocondition;

  /**充值可见性*/
  public final int rechargecondition;

  /**首充赠送描述*/
  public final String firstCharge;

  /**次充赠送描述*/
  public final String followupCharge;

  /**是否自动开启*/
  public final int autoOpenGift;

  /**额外展示列*/
  public final int extraShowItemId;

  /**开服区间*/
  public final int OpenServiceActivity;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public CommodityInfo2Config(int id, int type, String typePara, int grade, int school, int group, int sequence, String name, String des, int icon, String itemList, int limitType, int limitPara, int levelLowerLimit, int levelUpperLimit, int moneyType, int price, int priceShow, int PrePriceShow, int priceStepValue, int timeType, int activityID, String startTime, String endTime, String specialEndTime, int isShowTime, String sloganType, int startTimeTips, String startTimeWord, int endTimeTips, int groupId, int batch, int rechargeShopId, int rechargeShopId1, int tabshow, int herocondition, int rechargecondition, String firstCharge, String followupCharge, int autoOpenGift, int extraShowItemId, int OpenServiceActivity) {
    this.id = id;
    this.type = type;
    this.typePara = typePara;
    this.grade = grade;
    this.school = school;
    this.group = group;
    this.sequence = sequence;
    this.name = name;
    this.des = des;
    this.icon = icon;
    this.itemList = itemList;
    this.limitType = limitType;
    this.limitPara = limitPara;
    this.levelLowerLimit = levelLowerLimit;
    this.levelUpperLimit = levelUpperLimit;
    this.moneyType = moneyType;
    this.price = price;
    this.priceShow = priceShow;
    this.PrePriceShow = PrePriceShow;
    this.priceStepValue = priceStepValue;
    this.timeType = timeType;
    this.activityID = activityID;
    this.startTime = startTime;
    this.endTime = endTime;
    this.specialEndTime = specialEndTime;
    this.isShowTime = isShowTime;
    this.sloganType = sloganType;
    this.startTimeTips = startTimeTips;
    this.startTimeWord = startTimeWord;
    this.endTimeTips = endTimeTips;
    this.groupId = groupId;
    this.batch = batch;
    this.rechargeShopId = rechargeShopId;
    this.rechargeShopId1 = rechargeShopId1;
    this.tabshow = tabshow;
    this.herocondition = herocondition;
    this.rechargecondition = rechargecondition;
    this.firstCharge = firstCharge;
    this.followupCharge = followupCharge;
    this.autoOpenGift = autoOpenGift;
    this.extraShowItemId = extraShowItemId;
    this.OpenServiceActivity = OpenServiceActivity;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
