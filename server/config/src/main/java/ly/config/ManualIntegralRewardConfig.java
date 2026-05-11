package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ManualIntegralRewardConfig {
  /**编号*/
  public final int id;

  /**积分分组*/
  public final int group;

  /**对应积分*/
  public final int integral;

  /**奖励展示*/
  public final String rewardShow;

  /**实际掉落*/
  public final int drop;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ManualIntegralRewardConfig(int id, int group, int integral, String rewardShow, int drop) {
    this.id = id;
    this.group = group;
    this.integral = integral;
    this.rewardShow = rewardShow;
    this.drop = drop;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
