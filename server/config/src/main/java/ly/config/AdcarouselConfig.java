package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class AdcarouselConfig {
  public final int id;

  public final String beizhu;

  public final int adcarousel;

  public final int adcarousel2;

  public final int timeType;

  public final String startTime;

  public final String endTime;

  public final java.util.List<Integer> specialEndTime;

  public final int jump1;

  public final int order;

  public final java.util.List<Integer> level_limit;

  public final int OpenServiceActivity;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public AdcarouselConfig(int id, String beizhu, int adcarousel, int adcarousel2, int timeType, String startTime, String endTime, java.util.List<Integer> specialEndTime, int jump1, int order, java.util.List<Integer> level_limit, int OpenServiceActivity) {
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
