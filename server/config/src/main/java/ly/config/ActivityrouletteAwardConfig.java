package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityrouletteAwardConfig {
  /**序号*/
  public final int id;

  /**活动排期*/
  public final int scheDuling;

  /**奖池id*/
  public final int prizeId;

  /**奖励展示*/
  public final String rewardShow;

  /**实际掉落*/
  public final int drop;

  /**奖励权重*/
  public final int rewardWeight;

  /**奖励等级*/
  public final int rewardLevel;

  /**最高次数限制*/
  public final int highest;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityrouletteAwardConfig(int id, int scheDuling, int prizeId, String rewardShow, int drop, int rewardWeight, int rewardLevel, int highest) {
    this.id = id;
    this.scheDuling = scheDuling;
    this.prizeId = prizeId;
    this.rewardShow = rewardShow;
    this.drop = drop;
    this.rewardWeight = rewardWeight;
    this.rewardLevel = rewardLevel;
    this.highest = highest;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
