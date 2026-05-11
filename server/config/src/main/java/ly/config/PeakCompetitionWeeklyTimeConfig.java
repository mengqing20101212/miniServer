package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PeakCompetitionWeeklyTimeConfig {
  /**序号*/
  public final int id;

  /**期数*/
  public final int phase;

  /**单周开始时间*/
  public final String weekStartTime;

  /**单周结算时间*/
  public final String weekEndingTime;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PeakCompetitionWeeklyTimeConfig(int id, int phase, String weekStartTime, String weekEndingTime) {
    this.id = id;
    this.phase = phase;
    this.weekStartTime = weekStartTime;
    this.weekEndingTime = weekEndingTime;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
