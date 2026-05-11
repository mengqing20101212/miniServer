package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivitypassawardConfig {
  /**索引ID*/
  public final int id;

  /**活动排期*/
  public final int scheDuling;

  /**等级*/
  public final int level;

  /**购买等级所需彩钻*/
  public final int payLevel;

  /**商品ID*/
  public final int rechargeShopId;

  /**需要积分*/
  public final int score;

  /**免费奖励掉落*/
  public final int freeGift;

  /**免费奖励展示*/
  public final int freeGiftShow;

  /**付费奖励掉落*/
  public final int payGift;

  /**付费奖励展示*/
  public final int payGiftShow;

  /**奖励是否突出显示*/
  public final int redirectionId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivitypassawardConfig(int id, int scheDuling, int level, int payLevel, int rechargeShopId, int score, int freeGift, int freeGiftShow, int payGift, int payGiftShow, int redirectionId) {
    this.id = id;
    this.scheDuling = scheDuling;
    this.level = level;
    this.payLevel = payLevel;
    this.rechargeShopId = rechargeShopId;
    this.score = score;
    this.freeGift = freeGift;
    this.freeGiftShow = freeGiftShow;
    this.payGift = payGift;
    this.payGiftShow = payGiftShow;
    this.redirectionId = redirectionId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
