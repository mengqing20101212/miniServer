package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ResRecoveryConfig {
  /**ID*/
  public final int id;

  /**玩法类型*/
  public final int type;

  /**玩法进度*/
  public final int progress;

  /**单次奖励*/
  public final String eachReward;

  /**衰减比例*/
  public final int attenuationRate;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ResRecoveryConfig(int id, int type, int progress, String eachReward, int attenuationRate) {
    this.id = id;
    this.type = type;
    this.progress = progress;
    this.eachReward = eachReward;
    this.attenuationRate = attenuationRate;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
