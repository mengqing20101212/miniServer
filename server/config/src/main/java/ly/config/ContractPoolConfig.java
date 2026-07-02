package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ContractPoolConfig {
  /**招募编号*/
  public final int id;

  /**招募名称*/
  public final String name;

  /**单抽卡池id*/
  public final int oneDrawId;

  /**十抽卡池id*/
  public final int tenDrawId;

  /**概率文本*/
  public final String chanceText;

  /**标题条ID*/
  public final int topId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ContractPoolConfig(int id, String name, int oneDrawId, int tenDrawId, String chanceText, int topId) {
    this.id = id;
    this.name = name;
    this.oneDrawId = oneDrawId;
    this.tenDrawId = tenDrawId;
    this.chanceText = chanceText;
    this.topId = topId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
