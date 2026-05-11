package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PeakCompetitionSeasonTimeConfig {
  /**期数*/
  public final int id;

  /**赛季开始时间*/
  public final String seasonStartTime;

  /**赛季结束时间*/
  public final String seasonEndingTime;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PeakCompetitionSeasonTimeConfig(int id, String seasonStartTime, String seasonEndingTime) {
    this.id = id;
    this.seasonStartTime = seasonStartTime;
    this.seasonEndingTime = seasonEndingTime;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
