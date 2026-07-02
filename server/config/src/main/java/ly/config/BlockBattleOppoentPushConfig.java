package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class BlockBattleOppoentPushConfig {
  /**编号*/
  public final int id;

  /**分数段*/
  public final String grade;

  /**对手等级*/
  public final int levelOpponent;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public BlockBattleOppoentPushConfig(int id, String grade, int levelOpponent) {
    this.id = id;
    this.grade = grade;
    this.levelOpponent = levelOpponent;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
