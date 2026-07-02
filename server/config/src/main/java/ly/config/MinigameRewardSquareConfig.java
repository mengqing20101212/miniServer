package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class MinigameRewardSquareConfig {
  /**编号*/
  public final int id;

  /**展示评价*/
  public final String rankShow;

  /**血量区间*/
  public final String hpScore;

  /**时间区间*/
  public final String timeScore;

  /**奖励*/
  public final int dropId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public MinigameRewardSquareConfig(int id, String rankShow, String hpScore, String timeScore, int dropId) {
    this.id = id;
    this.rankShow = rankShow;
    this.hpScore = hpScore;
    this.timeScore = timeScore;
    this.dropId = dropId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
