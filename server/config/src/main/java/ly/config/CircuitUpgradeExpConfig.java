package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class CircuitUpgradeExpConfig {
  /**模板种类*/
  public final int upgradeType;

  /**等级*/
  public final int level;

  /**经验*/
  public final int exp;

  /**吞吃经验*/
  public final int eatExp;

  /**吞吃消耗钞票*/
  public final int consumeGold;

  /**新副属性概率*/
  public final int newSubAttrProb;

  /**升级消耗钞票*/
  public final int upgrageGold;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public CircuitUpgradeExpConfig(int upgradeType, int level, int exp, int eatExp, int consumeGold, int newSubAttrProb, int upgrageGold) {
    this.upgradeType = upgradeType;
    this.level = level;
    this.exp = exp;
    this.eatExp = eatExp;
    this.consumeGold = consumeGold;
    this.newSubAttrProb = newSubAttrProb;
    this.upgrageGold = upgrageGold;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
