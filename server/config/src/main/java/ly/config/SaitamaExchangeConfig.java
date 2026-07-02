package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SaitamaExchangeConfig {
  /**编号*/
  public final int id;

  /**兑换比例*/
  public final int ratio;

  /**可兑换卡片*/
  public final String changeList;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SaitamaExchangeConfig(int id, int ratio, String changeList) {
    this.id = id;
    this.ratio = ratio;
    this.changeList = changeList;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
