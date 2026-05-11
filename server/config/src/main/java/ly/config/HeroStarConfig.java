package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class HeroStarConfig {
  /**星级*/
  public final int star;

  /**模板id*/
  public final int modelId;

  /**等级上限*/
  public final int maxLevel;

  /**觉醒上限*/
  public final int awakenLevel;

  /**升星道具1*/
  public final String starItem;

  /**升星道具2*/
  public final String starItem2;

  /**分解返还道具*/
  public final String retainItem;

  /**分解消耗货币类型*/
  public final int currencyType;

  /**分解消耗货币数量*/
  public final String currencyNum;

  /**分解消耗物品*/
  public final String item;

  /**源核对应星级锁*/
  public final String circuitSlot;

  /**是否显示后续觉醒*/
  public final int followAwaken;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public HeroStarConfig(int star, int modelId, int maxLevel, int awakenLevel, String starItem, String starItem2, String retainItem, int currencyType, String currencyNum, String item, String circuitSlot, int followAwaken) {
    this.star = star;
    this.modelId = modelId;
    this.maxLevel = maxLevel;
    this.awakenLevel = awakenLevel;
    this.starItem = starItem;
    this.starItem2 = starItem2;
    this.retainItem = retainItem;
    this.currencyType = currencyType;
    this.currencyNum = currencyNum;
    this.item = item;
    this.circuitSlot = circuitSlot;
    this.followAwaken = followAwaken;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
