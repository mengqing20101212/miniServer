package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PassCheckConfig {
  /**编号*/
  public final int id;

  /**整备等级*/
  public final int level;

  /**升级经验*/
  public final int cost;

  /**基础奖励*/
  public final String reward;

  /**进阶奖励*/
  public final String seniorReward;

  /**特殊奖励*/
  public final int key;

  /**等级价格*/
  public final int levelPrice;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PassCheckConfig(int id, int level, int cost, String reward, String seniorReward, int key, int levelPrice) {
    this.id = id;
    this.level = level;
    this.cost = cost;
    this.reward = reward;
    this.seniorReward = seniorReward;
    this.key = key;
    this.levelPrice = levelPrice;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
