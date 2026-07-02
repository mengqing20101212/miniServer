package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ManualActiveConfig {
  /**编号*/
  public final int id;

  /**需要多少活跃度*/
  public final int point;

  /**展示图标道具id*/
  public final int iconId;

  /**奖励道具*/
  public final String reward;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ManualActiveConfig(int id, int point, int iconId, String reward) {
    this.id = id;
    this.point = point;
    this.iconId = iconId;
    this.reward = reward;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
