package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityControlConfig {
  /**编号*/
  public final int id;

  /**活动名称*/
  public final String name;

  /**解锁类型*/
  public final int unlockType;

  /**解锁参数*/
  public final int unlockPara;

  /**解锁参数2*/
  public final int unlockPara2;

  /**阵容id*/
  public final int lineupId;

  /**阵容限制*/
  public final int lineupLimit;

  /**是否是服务器战斗*/
  public final int isOnlineBattle;

  /**系统相关的BGM*/
  public final int BgmId;

  /**开启类型*/
  public final int openType;

  /**开启参数*/
  public final String openPara;

  /**新手引导id*/
  public final int guideId;

  /**图标*/
  public final int icon;

  /**描述*/
  public final String des;

  /**时间描述*/
  public final String timeDes;

  /**奖励展示*/
  public final String rewardId;

  /**未解锁提示*/
  public final String openLimitDes;

  /**跳转id*/
  public final int turnId;

  /**组队阵容id*/
  public final int lineupTeamId;

  /**是否在个人空间展示战绩*/
  public final int saveBattleLog;

  /**每日限制*/
  public final int dayLimit;

  /**每周限制*/
  public final int weekLimit;

  /**图标*/
  public final String activityIcon;

  /**名字*/
  public final String activityName;

  /**立绘+颜色*/
  public final String bgColour;

  /**奖励类型*/
  public final String activityreward;

  /**组队推荐等级*/
  public final String teamLv;

  /**帮助跳转*/
  public final int help;

  /**是否屏幕预解锁*/
  public final int noPrelock;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityControlConfig(int id, String name, int unlockType, int unlockPara, int unlockPara2, int lineupId, int lineupLimit, int isOnlineBattle, int BgmId, int openType, String openPara, int guideId, int icon, String des, String timeDes, String rewardId, String openLimitDes, int turnId, int lineupTeamId, int saveBattleLog, int dayLimit, int weekLimit, String activityIcon, String activityName, String bgColour, String activityreward, String teamLv, int help, int noPrelock) {
    this.id = id;
    this.name = name;
    this.unlockType = unlockType;
    this.unlockPara = unlockPara;
    this.unlockPara2 = unlockPara2;
    this.lineupId = lineupId;
    this.lineupLimit = lineupLimit;
    this.isOnlineBattle = isOnlineBattle;
    this.BgmId = BgmId;
    this.openType = openType;
    this.openPara = openPara;
    this.guideId = guideId;
    this.icon = icon;
    this.des = des;
    this.timeDes = timeDes;
    this.rewardId = rewardId;
    this.openLimitDes = openLimitDes;
    this.turnId = turnId;
    this.lineupTeamId = lineupTeamId;
    this.saveBattleLog = saveBattleLog;
    this.dayLimit = dayLimit;
    this.weekLimit = weekLimit;
    this.activityIcon = activityIcon;
    this.activityName = activityName;
    this.bgColour = bgColour;
    this.activityreward = activityreward;
    this.teamLv = teamLv;
    this.help = help;
    this.noPrelock = noPrelock;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
