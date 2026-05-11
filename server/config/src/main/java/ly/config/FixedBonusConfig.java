package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class FixedBonusConfig {
  /**编号*/
  public final int id;

  /**固定Bonus组*/
  public final int fixedGroup;

  /**Bonus编号*/
  public final int bonusId;

  /**回合数*/
  public final int turn;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public FixedBonusConfig(int id, int fixedGroup, int bonusId, int turn) {
    this.id = id;
    this.fixedGroup = fixedGroup;
    this.bonusId = bonusId;
    this.turn = turn;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
