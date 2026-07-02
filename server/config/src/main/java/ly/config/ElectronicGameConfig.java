package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ElectronicGameConfig {
  /**编号*/
  public final int id;

  /**段位*/
  public final String duan;

  /**大段名*/
  public final String duanBigName;

  /**大段位*/
  public final int duanBig;

  /**小段位*/
  public final int duanSmall;

  /**段位图标*/
  public final int duanIconBig;

  /**段位图标*/
  public final int duanIconSmall;

  /**星级数量*/
  public final int starNum;

  /**累积星数*/
  public final int starCollect;

  /**ELO分K值*/
  public final float eloKValue;

  /**勇者积分上限*/
  public final int scoreMax;

  /**保星积分*/
  public final int scoreProtection;

  /**是否开启积分保星*/
  public final int isScoreProtection;

  /**连胜加星*/
  public final int winningStreak;

  /**掉段保护*/
  public final int dropProtection;

  /**保大段*/
  public final int duanProtection;

  /**精确匹配分数*/
  public final int bestMatchScore;

  /**精确匹配时间*/
  public final int bestMatchTime;

  /**精确匹配星数*/
  public final int bestMatchStar;

  /**模糊匹配分数*/
  public final int fuzzyMatchScore;

  /**模糊匹配时间*/
  public final int fuzzyMatchTime;

  /**模糊匹配星数*/
  public final int fuzzyMatchStar;

  /**保底匹配分数*/
  public final int leastMatchScore;

  /**保底匹配时间*/
  public final int leastMatchTime;

  /**保底匹配星数*/
  public final int leastMatchStar;

  /**是否超时保底匹配机器人*/
  public final int isTimeOutRebotMatch;

  /**是否战败保底匹配机器人*/
  public final int isLoseRobotMatch;

  /**是否轮选*/
  public final int isPick;

  /**战斗胜利荣誉*/
  public final int winReward;

  /**战斗失败荣誉*/
  public final int loseReward;

  /**每周荣誉上限*/
  public final int honorLimit;

  /**每周结算奖励*/
  public final String awardWeek;

  /**每周结算奖励预览*/
  public final String awardWeekPre;

  /**匹配服务器*/
  public final String bossTimeSetting;

  /**对应机器人范围*/
  public final int robotPool;

  /**对应场次*/
  public final int fairSceneMatch1;

  /**对应场次*/
  public final int SceneMatch3v3;

  /**段位奖励*/
  public final int drop;

  /**段位奖励前端*/
  public final int dropShow;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ElectronicGameConfig(int id, String duan, String duanBigName, int duanBig, int duanSmall, int duanIconBig, int duanIconSmall, int starNum, int starCollect, float eloKValue, int scoreMax, int scoreProtection, int isScoreProtection, int winningStreak, int dropProtection, int duanProtection, int bestMatchScore, int bestMatchTime, int bestMatchStar, int fuzzyMatchScore, int fuzzyMatchTime, int fuzzyMatchStar, int leastMatchScore, int leastMatchTime, int leastMatchStar, int isTimeOutRebotMatch, int isLoseRobotMatch, int isPick, int winReward, int loseReward, int honorLimit, String awardWeek, String awardWeekPre, String bossTimeSetting, int robotPool, int fairSceneMatch1, int SceneMatch3v3, int drop, int dropShow) {
    this.id = id;
    this.duan = duan;
    this.duanBigName = duanBigName;
    this.duanBig = duanBig;
    this.duanSmall = duanSmall;
    this.duanIconBig = duanIconBig;
    this.duanIconSmall = duanIconSmall;
    this.starNum = starNum;
    this.starCollect = starCollect;
    this.eloKValue = eloKValue;
    this.scoreMax = scoreMax;
    this.scoreProtection = scoreProtection;
    this.isScoreProtection = isScoreProtection;
    this.winningStreak = winningStreak;
    this.dropProtection = dropProtection;
    this.duanProtection = duanProtection;
    this.bestMatchScore = bestMatchScore;
    this.bestMatchTime = bestMatchTime;
    this.bestMatchStar = bestMatchStar;
    this.fuzzyMatchScore = fuzzyMatchScore;
    this.fuzzyMatchTime = fuzzyMatchTime;
    this.fuzzyMatchStar = fuzzyMatchStar;
    this.leastMatchScore = leastMatchScore;
    this.leastMatchTime = leastMatchTime;
    this.leastMatchStar = leastMatchStar;
    this.isTimeOutRebotMatch = isTimeOutRebotMatch;
    this.isLoseRobotMatch = isLoseRobotMatch;
    this.isPick = isPick;
    this.winReward = winReward;
    this.loseReward = loseReward;
    this.honorLimit = honorLimit;
    this.awardWeek = awardWeek;
    this.awardWeekPre = awardWeekPre;
    this.bossTimeSetting = bossTimeSetting;
    this.robotPool = robotPool;
    this.fairSceneMatch1 = fairSceneMatch1;
    this.SceneMatch3v3 = SceneMatch3v3;
    this.drop = drop;
    this.dropShow = dropShow;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
