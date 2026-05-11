package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ContactMainConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String name;

  /**英文名字*/
  public final String englishName;

  /**解锁类型*/
  public final String missionList;

  /**立绘*/
  public final int resource;

  /**头像*/
  public final int headIcon;

  /**解锁条件展示*/
  public final String missionShow;

  /**奖励预览展示*/
  public final String rewardShow;

  /**每日挑战次数*/
  public final int challengeTimes;

  /**休息日*/
  public final String weekend;

  /**休息挑战次数*/
  public final int weekendChallenge;

  /**每次消耗体力*/
  public final int stamina;

  /**事件池*/
  public final String eventPool;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ContactMainConfig(int id, String name, String englishName, String missionList, int resource, int headIcon, String missionShow, String rewardShow, int challengeTimes, String weekend, int weekendChallenge, int stamina, String eventPool) {
    this.id = id;
    this.name = name;
    this.englishName = englishName;
    this.missionList = missionList;
    this.resource = resource;
    this.headIcon = headIcon;
    this.missionShow = missionShow;
    this.rewardShow = rewardShow;
    this.challengeTimes = challengeTimes;
    this.weekend = weekend;
    this.weekendChallenge = weekendChallenge;
    this.stamina = stamina;
    this.eventPool = eventPool;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
