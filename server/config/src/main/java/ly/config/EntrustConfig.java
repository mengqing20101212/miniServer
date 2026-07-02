package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class EntrustConfig {
  /**id*/
  public final int id;

  /**所属组别*/
  public final int groupId;

  /**星级*/
  public final int star;

  /**解锁类型*/
  public final int unlockType;

  /**解锁条件*/
  public final int unlockCondition;

  /**解锁条件说明*/
  public final String unlockDec;

  /**主要奖励*/
  public final String reward;

  /**额外奖励*/
  public final int drop;

  /**额外奖励概率*/
  public final int dropPercent;

  /**时间上限*/
  public final int time;

  /**加成类型*/
  public final int type;

  /**类型加成千分比*/
  public final int percent;

  /**上阵数量*/
  public final int heroNum;

  /**任务名称*/
  public final String taskName;

  /**时间段(结束点)(分钟)*/
  public final String timeGroup;

  /**挂机时间对应动作( 图片)*/
  public final String action;

  /**节点位置(x,y)*/
  public final String position;

  /**图标*/
  public final int iconResId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public EntrustConfig(int id, int groupId, int star, int unlockType, int unlockCondition, String unlockDec, String reward, int drop, int dropPercent, int time, int type, int percent, int heroNum, String taskName, String timeGroup, String action, String position, int iconResId) {
    this.id = id;
    this.groupId = groupId;
    this.star = star;
    this.unlockType = unlockType;
    this.unlockCondition = unlockCondition;
    this.unlockDec = unlockDec;
    this.reward = reward;
    this.drop = drop;
    this.dropPercent = dropPercent;
    this.time = time;
    this.type = type;
    this.percent = percent;
    this.heroNum = heroNum;
    this.taskName = taskName;
    this.timeGroup = timeGroup;
    this.action = action;
    this.position = position;
    this.iconResId = iconResId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
