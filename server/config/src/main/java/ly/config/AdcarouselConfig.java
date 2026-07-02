package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class AdcarouselConfig {
  /**索引ID*/
  public final int id;

  /**备注*/
  public final String beizhu;

  /**轮播图ID*/
  public final int adcarousel;

  /**轮播图ID*/
  public final int adcarousel2;

  /**时间类型*/
  public final int timeType;

  /**开始时间*/
  public final String startTime;

  /**结束时间*/
  public final String endTime;

  /**特殊时间*/
  public final String specialEndTime;

  /**跳转功能*/
  public final int jump1;

  /**展板排序*/
  public final int order;

  /**等级可见性*/
  public final String level_limit;

  /**开服区间*/
  public final int OpenServiceActivity;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public AdcarouselConfig(int id, String beizhu, int adcarousel, int adcarousel2, int timeType, String startTime, String endTime, String specialEndTime, int jump1, int order, String level_limit, int OpenServiceActivity) {
    this.id = id;
    this.beizhu = beizhu;
    this.adcarousel = adcarousel;
    this.adcarousel2 = adcarousel2;
    this.timeType = timeType;
    this.startTime = startTime;
    this.endTime = endTime;
    this.specialEndTime = specialEndTime;
    this.jump1 = jump1;
    this.order = order;
    this.level_limit = level_limit;
    this.OpenServiceActivity = OpenServiceActivity;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
