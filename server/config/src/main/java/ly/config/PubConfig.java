package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PubConfig {
  /**id*/
  public final int id;

  /**可领取时间*/
  public final String time;

  /**领取内容*/
  public final int drop;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PubConfig(int id, String time, int drop) {
    this.id = id;
    this.time = time;
    this.drop = drop;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
