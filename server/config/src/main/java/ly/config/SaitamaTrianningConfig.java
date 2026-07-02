package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SaitamaTrianningConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String name;

  /**图标*/
  public final int icon;

  /**开启时长（分钟）*/
  public final int openTime;

  /**奖励*/
  public final String reward;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SaitamaTrianningConfig(int id, String name, int icon, int openTime, String reward) {
    this.id = id;
    this.name = name;
    this.icon = icon;
    this.openTime = openTime;
    this.reward = reward;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
