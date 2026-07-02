package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityPuzzleConfig {
  /**编号*/
  public final int id;

  /**拼图期数*/
  public final int Puzzleid;

  /**消耗道具数量*/
  public final String lossItem;

  /**拼图碎片奖励*/
  public final int poolGift;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityPuzzleConfig(int id, int Puzzleid, String lossItem, int poolGift) {
    this.id = id;
    this.Puzzleid = Puzzleid;
    this.lossItem = lossItem;
    this.poolGift = poolGift;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
