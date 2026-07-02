package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PeakCompetitionSeasonRewardConfig {
  /**编号*/
  public final int id;

  /**分数段*/
  public final String grade;

  /**分数段展示*/
  public final String gradeShow;

  /**每赛季结算奖励*/
  public final int awardSeason;

  /**每赛季结算奖励预览*/
  public final String awardPre;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PeakCompetitionSeasonRewardConfig(int id, String grade, String gradeShow, int awardSeason, String awardPre) {
    this.id = id;
    this.grade = grade;
    this.gradeShow = gradeShow;
    this.awardSeason = awardSeason;
    this.awardPre = awardPre;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
