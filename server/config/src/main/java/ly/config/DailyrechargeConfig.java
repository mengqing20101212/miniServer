package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DailyrechargeConfig {
  /**索引ID*/
  public final int id;

  /**任务阶段*/
  public final int stage;

  /**天数*/
  public final int Days;

  /**奖励展示*/
  public final String rewardShow;

  /**实际掉落*/
  public final int drop;

  /**显示道具*/
  public final int rechargShow;

  /**免费礼包*/
  public final int rewardsfree;

  /**终极礼包*/
  public final int rewardsfinally;

  /**实际掉落*/
  public final int finallydrop;

  /**充值ID*/
  public final int RechargeShop;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DailyrechargeConfig(int id, int stage, int Days, String rewardShow, int drop, int rechargShow, int rewardsfree, int rewardsfinally, int finallydrop, int RechargeShop) {
    this.id = id;
    this.stage = stage;
    this.Days = Days;
    this.rewardShow = rewardShow;
    this.drop = drop;
    this.rechargShow = rechargShow;
    this.rewardsfree = rewardsfree;
    this.rewardsfinally = rewardsfinally;
    this.finallydrop = finallydrop;
    this.RechargeShop = RechargeShop;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
