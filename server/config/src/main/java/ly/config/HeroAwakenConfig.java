package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class HeroAwakenConfig {
  /**编号*/
  public final int id;

  /**英雄模板名*/
  public final int modelName;

  /**觉醒次序*/
  public final int sequence;

  /**觉醒类型*/
  public final int awakenType;

  /**觉醒后常规技能*/
  public final int awakenskill_1;

  /**  null*/
  public final int awakenskill_2;

  /**  null*/
  public final int awakenskill_3;

  /**觉醒超级技能*/
  public final int awakenskill_s1;

  /**觉醒超级技能*/
  public final int awakenskill_s2;

  /**多段觉醒技能*/
  public final int moreAwakenSkill1;

  /**多段觉醒技能*/
  public final int moreAwakenSkill2;

  /**多段觉醒技能*/
  public final int moreAwakenSkill3;

  /**属性类型*/
  public final int attrType;

  /**属性数值*/
  public final int attrNum;

  /**觉醒阶段*/
  public final String awakenPhase;

  /**觉醒图标*/
  public final int awakenIcon;

  /**觉醒标题*/
  public final String awakenTitle;

  /**觉醒属性描述*/
  public final String awakenAttrDes;

  /**觉醒材料需求*/
  public final String awakenItem;

  /**觉醒货币类型*/
  public final int awakenCurrencyType;

  /**觉醒货币数量*/
  public final int awakenCurrencyNum;

  /**是否还原*/
  public final int isReset;

  /**还原返还*/
  public final String retainItem;

  /**分解消耗货币类型*/
  public final int currencyType;

  /**分解消耗货币数量*/
  public final int currencyNum;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public HeroAwakenConfig(int id, int modelName, int sequence, int awakenType, int awakenskill_1, int awakenskill_2, int awakenskill_3, int awakenskill_s1, int awakenskill_s2, int moreAwakenSkill1, int moreAwakenSkill2, int moreAwakenSkill3, int attrType, int attrNum, String awakenPhase, int awakenIcon, String awakenTitle, String awakenAttrDes, String awakenItem, int awakenCurrencyType, int awakenCurrencyNum, int isReset, String retainItem, int currencyType, int currencyNum) {
    this.id = id;
    this.modelName = modelName;
    this.sequence = sequence;
    this.awakenType = awakenType;
    this.awakenskill_1 = awakenskill_1;
    this.awakenskill_2 = awakenskill_2;
    this.awakenskill_3 = awakenskill_3;
    this.awakenskill_s1 = awakenskill_s1;
    this.awakenskill_s2 = awakenskill_s2;
    this.moreAwakenSkill1 = moreAwakenSkill1;
    this.moreAwakenSkill2 = moreAwakenSkill2;
    this.moreAwakenSkill3 = moreAwakenSkill3;
    this.attrType = attrType;
    this.attrNum = attrNum;
    this.awakenPhase = awakenPhase;
    this.awakenIcon = awakenIcon;
    this.awakenTitle = awakenTitle;
    this.awakenAttrDes = awakenAttrDes;
    this.awakenItem = awakenItem;
    this.awakenCurrencyType = awakenCurrencyType;
    this.awakenCurrencyNum = awakenCurrencyNum;
    this.isReset = isReset;
    this.retainItem = retainItem;
    this.currencyType = currencyType;
    this.currencyNum = currencyNum;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
