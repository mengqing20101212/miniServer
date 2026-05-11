package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SaitamaLevelConfig {
  /**编号*/
  public final int id;

  /**到下一级经验*/
  public final int nextLv;

  /**每日好感度礼包*/
  public final int dailyGift;

  /**每日好感度礼包展示*/
  public final int dailyGiftShow;

  /**每日好感度礼包领取次数*/
  public final int giftTimes;

  /**埼玉卡片等级*/
  public final int summonLevel;

  /**加成id*/
  public final int exdropLevel;

  /**料理星级*/
  public final int cookStar;

  /**料理合成加成系数*/
  public final int cookmixPara;

  /**料理暴击概率（%）*/
  public final int cookCritical;

  /**料理解锁栏位数*/
  public final int cookSlot;

  /**体力储存池上限*/
  public final int overflowMax;

  /**体力溢出转换率(%)*/
  public final int conversionRate;

  /**每日彩蛋领取奖励最大次数*/
  public final int eggRewardTimes;

  /**锻炼奖励等级*/
  public final int trianningRewardLv;

  /**信息列表*/
  public final String infoList;

  /**升级信息预告列表*/
  public final String upgradeInfo;

  /**好感度入口头像*/
  public final int headIcon;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SaitamaLevelConfig(int id, int nextLv, int dailyGift, int dailyGiftShow, int giftTimes, int summonLevel, int exdropLevel, int cookStar, int cookmixPara, int cookCritical, int cookSlot, int overflowMax, int conversionRate, int eggRewardTimes, int trianningRewardLv, String infoList, String upgradeInfo, int headIcon) {
    this.id = id;
    this.nextLv = nextLv;
    this.dailyGift = dailyGift;
    this.dailyGiftShow = dailyGiftShow;
    this.giftTimes = giftTimes;
    this.summonLevel = summonLevel;
    this.exdropLevel = exdropLevel;
    this.cookStar = cookStar;
    this.cookmixPara = cookmixPara;
    this.cookCritical = cookCritical;
    this.cookSlot = cookSlot;
    this.overflowMax = overflowMax;
    this.conversionRate = conversionRate;
    this.eggRewardTimes = eggRewardTimes;
    this.trianningRewardLv = trianningRewardLv;
    this.infoList = infoList;
    this.upgradeInfo = upgradeInfo;
    this.headIcon = headIcon;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
