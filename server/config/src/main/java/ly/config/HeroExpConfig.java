package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class HeroExpConfig {
  /**等级*/
  public final int level;

  /**升级模板id*/
  public final int modelId;

  /**经验*/
  public final int exp;

  /**分解经验*/
  public final int retainExp;

  /**分解消耗货币类型*/
  public final int currencyType;

  /**分解消耗货币数量*/
  public final String currencyNum;

  /**分解消耗物品*/
  public final String item;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public HeroExpConfig(int level, int modelId, int exp, int retainExp, int currencyType, String currencyNum, String item) {
    this.level = level;
    this.modelId = modelId;
    this.exp = exp;
    this.retainExp = retainExp;
    this.currencyType = currencyType;
    this.currencyNum = currencyNum;
    this.item = item;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
