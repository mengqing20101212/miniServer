package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class OppoentRewardConfig {
  /**编号*/
  public final int id;

  /**排名*/
  public final String rank;

  /**奖励*/
  public final int reward;

  /**奖励显示*/
  public final int rewardShow;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public OppoentRewardConfig(int id, String rank, int reward, int rewardShow) {
    this.id = id;
    this.rank = rank;
    this.reward = reward;
    this.rewardShow = rewardShow;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
