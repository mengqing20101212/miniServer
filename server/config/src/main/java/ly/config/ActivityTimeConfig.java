package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityTimeConfig {
  /**编号*/
  public final int id;

  /**功能名称*/
  public final String name;

  /**时间类型*/
  public final int type;

  /**活动时间*/
  public final String time;

  /**活动时间（显示）*/
  public final String timeShow;

  /**开放星期*/
  public final String week;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityTimeConfig(int id, String name, int type, String time, String timeShow, String week) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.time = time;
    this.timeShow = timeShow;
    this.week = week;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
