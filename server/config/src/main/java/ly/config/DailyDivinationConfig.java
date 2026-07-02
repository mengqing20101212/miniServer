package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DailyDivinationConfig {
  /**ID*/
  public final int id;

  /**占卜次数*/
  public final int times;

  /**奖励道具*/
  public final int reward;

  /**奖励数量*/
  public final int num;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DailyDivinationConfig(int id, int times, int reward, int num) {
    this.id = id;
    this.times = times;
    this.reward = reward;
    this.num = num;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
