package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SaitamaActionTimeConfig {
  /**编号*/
  public final int id;

  /**组*/
  public final int group;

  /**埼玉动作池*/
  public final String actionPool1;

  /**杰诺斯动作池*/
  public final String actionPool2;

  /**在线时间段*/
  public final String time;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SaitamaActionTimeConfig(int id, int group, String actionPool1, String actionPool2, String time) {
    this.id = id;
    this.group = group;
    this.actionPool1 = actionPool1;
    this.actionPool2 = actionPool2;
    this.time = time;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
