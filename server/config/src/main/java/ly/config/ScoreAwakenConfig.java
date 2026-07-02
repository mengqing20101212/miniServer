package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ScoreAwakenConfig {
  /**id*/
  public final int id;

  /**品质*/
  public final int quality;

  /**觉醒等级*/
  public final int awaken;

  /**分数*/
  public final int score;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ScoreAwakenConfig(int id, int quality, int awaken, int score) {
    this.id = id;
    this.quality = quality;
    this.awaken = awaken;
    this.score = score;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
