package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivitygashaponConfig {
  /**编号*/
  public final int id;

  /**活动排期*/
  public final int scheDuling;

  /**消耗道具ID*/
  public final int itemId;

  /**消耗道具数量*/
  public final int count;

  /**扭蛋品级权重*/
  public final String gashaponWeights;

  /**扭蛋箱*/
  public final String gachaBox;

  /**奖励展示*/
  public final int RewardShow;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivitygashaponConfig(int id, int scheDuling, int itemId, int count, String gashaponWeights, String gachaBox, int RewardShow) {
    this.id = id;
    this.scheDuling = scheDuling;
    this.itemId = itemId;
    this.count = count;
    this.gashaponWeights = gashaponWeights;
    this.gachaBox = gachaBox;
    this.RewardShow = RewardShow;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
