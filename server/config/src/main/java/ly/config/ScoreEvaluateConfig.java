package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ScoreEvaluateConfig {
  /**id*/
  public final int id;

  /**评分*/
  public final float score;

  /**评价*/
  public final String evaluate;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ScoreEvaluateConfig(int id, float score, String evaluate) {
    this.id = id;
    this.score = score;
    this.evaluate = evaluate;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
