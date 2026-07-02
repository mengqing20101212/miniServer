package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityRouteConfig {
  /**编号*/
  public final int id;

  /**积分奖励*/
  public final int drop;

  /**积分奖励展示*/
  public final String rewardShow;

  /**消耗积分*/
  public final int score;

  /**所属位置(列)*/
  public final int array;

  /**所属位置(排)*/
  public final int row;

  /**前置档位*/
  public final String front;

  /**付费奖励*/
  public final int payGift;

  /**付费奖励展示*/
  public final String payGiftShow;

  /**充值ID*/
  public final int rechargeId;

  /**是否是交叉点*/
  public final int isCrossNode;

  /**付费积分奖励*/
  public final int drop2;

  /**积分奖励展示*/
  public final String rewardShow2;

  /**线坐标*/
  public final String line;

  /**圈坐标*/
  public final String circle;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityRouteConfig(int id, int drop, String rewardShow, int score, int array, int row, String front, int payGift, String payGiftShow, int rechargeId, int isCrossNode, int drop2, String rewardShow2, String line, String circle) {
    this.id = id;
    this.drop = drop;
    this.rewardShow = rewardShow;
    this.score = score;
    this.array = array;
    this.row = row;
    this.front = front;
    this.payGift = payGift;
    this.payGiftShow = payGiftShow;
    this.rechargeId = rechargeId;
    this.isCrossNode = isCrossNode;
    this.drop2 = drop2;
    this.rewardShow2 = rewardShow2;
    this.line = line;
    this.circle = circle;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
