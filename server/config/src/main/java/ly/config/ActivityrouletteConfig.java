package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityrouletteConfig {
  /**序号*/
  public final int id;

  /**活动期数*/
  public final int scheDuling;

  /**奖池id*/
  public final int prizeId;

  /**幸运值大奖档位*/
  public final int luckyPrize;

  /**每次抽奖获得积分*/
  public final int rafflePoints;

  /**触发珍品所需最低积分*/
  public final int minimumPoints;

  /**单次抽奖消耗道具ID*/
  public final int consumableProps;

  /**单次抽奖消耗道具数量*/
  public final int consumptionQuantity;

  /**幸运值上限*/
  public final int luckValueCap;

  /**抽中幸运值大奖后幸运值是否重置*/
  public final int resetLuck;

  /**每次抽奖获得幸运值*/
  public final int getLucky;

  /**10连保底*/
  public final int lotteryGuarantee;

  /**奖励展示*/
  public final String rewardShow;

  /**实际掉落*/
  public final int drop;

  /**幸运值珍品奖励次数*/
  public final int numberAwards;

  /**立绘*/
  public final String picture;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityrouletteConfig(int id, int scheDuling, int prizeId, int luckyPrize, int rafflePoints, int minimumPoints, int consumableProps, int consumptionQuantity, int luckValueCap, int resetLuck, int getLucky, int lotteryGuarantee, String rewardShow, int drop, int numberAwards, String picture) {
    this.id = id;
    this.scheDuling = scheDuling;
    this.prizeId = prizeId;
    this.luckyPrize = luckyPrize;
    this.rafflePoints = rafflePoints;
    this.minimumPoints = minimumPoints;
    this.consumableProps = consumableProps;
    this.consumptionQuantity = consumptionQuantity;
    this.luckValueCap = luckValueCap;
    this.resetLuck = resetLuck;
    this.getLucky = getLucky;
    this.lotteryGuarantee = lotteryGuarantee;
    this.rewardShow = rewardShow;
    this.drop = drop;
    this.numberAwards = numberAwards;
    this.picture = picture;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
