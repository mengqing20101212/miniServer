package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class BlockBattleChallengeRewardConfig {
  /**编号*/
  public final int id;

  /**声望数量*/
  public final int prestigeNum;

  /**任务描述*/
  public final String des;

  /**奖励*/
  public final int drop;

  /**奖励展示*/
  public final int dropShow;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public BlockBattleChallengeRewardConfig(int id, int prestigeNum, String des, int drop, int dropShow) {
    this.id = id;
    this.prestigeNum = prestigeNum;
    this.des = des;
    this.drop = drop;
    this.dropShow = dropShow;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
