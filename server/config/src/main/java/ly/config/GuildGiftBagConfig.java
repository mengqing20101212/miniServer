package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuildGiftBagConfig {
  /**ID*/
  public final int id;

  /**礼包类型*/
  public final int type;

  /**名字*/
  public final String name;

  /**掉落*/
  public final int drop;

  /**单次消耗钻石*/
  public final int cost;

  /**单次奖励声望*/
  public final int reward;

  /**领取人数*/
  public final int Num;

  /**英雄ID*/
  public final String heroId;

  /**活跃度*/
  public final int active;

  /**充值金额*/
  public final int recharge;

  /**礼包图标*/
  public final int giftIcon;

  /**标签*/
  public final int tag;

  /**红包分档名称显示*/
  public final String grading;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuildGiftBagConfig(int id, int type, String name, int drop, int cost, int reward, int Num, String heroId, int active, int recharge, int giftIcon, int tag, String grading) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.drop = drop;
    this.cost = cost;
    this.reward = reward;
    this.Num = Num;
    this.heroId = heroId;
    this.active = active;
    this.recharge = recharge;
    this.giftIcon = giftIcon;
    this.tag = tag;
    this.grading = grading;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
