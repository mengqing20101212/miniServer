package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityCommendgiftConfig {
  /**ID*/
  public final int id;

  /**表彰等级*/
  public final int CommendLevel;

  /**表彰类型*/
  public final int Commendtype;

  /**免费奖励展示*/
  public final int RewardShow;

  /**实际掉落*/
  public final int drop;

  /**付费奖励展示*/
  public final int rechargShow;

  /**实际掉落*/
  public final int rechargdrop;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityCommendgiftConfig(int id, int CommendLevel, int Commendtype, int RewardShow, int drop, int rechargShow, int rechargdrop) {
    this.id = id;
    this.CommendLevel = CommendLevel;
    this.Commendtype = Commendtype;
    this.RewardShow = RewardShow;
    this.drop = drop;
    this.rechargShow = rechargShow;
    this.rechargdrop = rechargdrop;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
