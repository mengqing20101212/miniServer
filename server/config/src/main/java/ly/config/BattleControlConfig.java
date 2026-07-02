package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class BattleControlConfig {
  /**编号*/
  public final int id;

  /**活动名称*/
  public final String name;

  /**阵容id*/
  public final int lineupId;

  /**是否是服务器战斗*/
  public final int isOnlineBattle;

  /**在线战斗通用LOADING界面最大等待时间*/
  public final int loadingTime;

  /**战前准备时间限制*/
  public final int readyTime;

  /**战前换阵容时间限制（单位s）*/
  public final int changeHeroTime;

  /**时间是否共用*/
  public final int isShare;

  /**每回合行动时间限制（单位s）*/
  public final int chaoiceWaitTime;

  /**是否保存战斗数据*/
  public final int isSaveDate;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public BattleControlConfig(int id, String name, int lineupId, int isOnlineBattle, int loadingTime, int readyTime, int changeHeroTime, int isShare, int chaoiceWaitTime, int isSaveDate) {
    this.id = id;
    this.name = name;
    this.lineupId = lineupId;
    this.isOnlineBattle = isOnlineBattle;
    this.loadingTime = loadingTime;
    this.readyTime = readyTime;
    this.changeHeroTime = changeHeroTime;
    this.isShare = isShare;
    this.chaoiceWaitTime = chaoiceWaitTime;
    this.isSaveDate = isSaveDate;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
