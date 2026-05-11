package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PeakCompetitionWeeklyRewardConfig {
  /**编号*/
  public final int id;

  /**分数段*/
  public final String grade;

  /**分数段展示*/
  public final String gradeShow;

  /**每周结算奖励*/
  public final String awardWeek;

  /**每周结算奖励预览*/
  public final String awardPre;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PeakCompetitionWeeklyRewardConfig(int id, String grade, String gradeShow, String awardWeek, String awardPre) {
    this.id = id;
    this.grade = grade;
    this.gradeShow = gradeShow;
    this.awardWeek = awardWeek;
    this.awardPre = awardPre;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
