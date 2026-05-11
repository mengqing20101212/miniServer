package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class HeadFrameConfig {
  /**编号*/
  public final int id;

  /**名字*/
  public final String name;

  /**描述*/
  public final String desc;

  /**排序*/
  public final int priority;

  /**资源名*/
  public final String value;

  /**持续时间*/
  public final int duration;

  /**是否默认开启*/
  public final int isInitial;

  /**渠道*/
  public final int channel;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public HeadFrameConfig(int id, String name, String desc, int priority, String value, int duration, int isInitial, int channel) {
    this.id = id;
    this.name = name;
    this.desc = desc;
    this.priority = priority;
    this.value = value;
    this.duration = duration;
    this.isInitial = isInitial;
    this.channel = channel;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
