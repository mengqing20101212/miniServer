package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class BlockBattleDailyRewardConfig {
  /**编号*/
  public final int id;

  /**分数段*/
  public final String grade;

  /**奖励*/
  public final int drop;

  /**奖励展示*/
  public final int dropShow;

  /**分数段展示*/
  public final String gradeShow;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public BlockBattleDailyRewardConfig(int id, String grade, int drop, int dropShow, String gradeShow) {
    this.id = id;
    this.grade = grade;
    this.drop = drop;
    this.dropShow = dropShow;
    this.gradeShow = gradeShow;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
