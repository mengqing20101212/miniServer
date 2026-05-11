package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class LimitedGitConditionConfig {
  /**编号*/
  public final int id;

  /**描述*/
  public final String des;

  /**条件ID*/
  public final int missionId;

  /**条件ID*/
  public final int missionType;

  /**触发最小等级*/
  public final int levelMin;

  /**触发最大等级*/
  public final int levelMax;

  /**重复触发次数*/
  public final int isRepeat;

  /**触发礼包*/
  public final int RechargeShopId;

  /**限时礼包过期时间*/
  public final int giveCloseTime;

  /**重新触发时间*/
  public final int cycleTime;

  /**触发时间类型*/
  public final int activateType;

  /**最大可触发次数*/
  public final int activateMax;

  /**英雄可见性*/
  public final int herocondition;

  /**充值可见性*/
  public final int rechargecondition;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public LimitedGitConditionConfig(int id, String des, int missionId, int missionType, int levelMin, int levelMax, int isRepeat, int RechargeShopId, int giveCloseTime, int cycleTime, int activateType, int activateMax, int herocondition, int rechargecondition) {
    this.id = id;
    this.des = des;
    this.missionId = missionId;
    this.missionType = missionType;
    this.levelMin = levelMin;
    this.levelMax = levelMax;
    this.isRepeat = isRepeat;
    this.RechargeShopId = RechargeShopId;
    this.giveCloseTime = giveCloseTime;
    this.cycleTime = cycleTime;
    this.activateType = activateType;
    this.activateMax = activateMax;
    this.herocondition = herocondition;
    this.rechargecondition = rechargecondition;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
