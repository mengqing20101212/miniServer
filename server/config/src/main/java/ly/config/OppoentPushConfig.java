package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class OppoentPushConfig {
  /**编号*/
  public final int id;

  /**轮次*/
  public final int rounds;

  /**自己等级*/
  public final int levelMy;

  /**对手等级*/
  public final int levelOpponent;

  /**真人困难*/
  public final int difficultNum;

  /**真人普通*/
  public final int normalNum;

  /**真人简单*/
  public final int easyNum;

  /**配置机器人数量*/
  public final int robotNum;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public OppoentPushConfig(int id, int rounds, int levelMy, int levelOpponent, int difficultNum, int normalNum, int easyNum, int robotNum) {
    this.id = id;
    this.rounds = rounds;
    this.levelMy = levelMy;
    this.levelOpponent = levelOpponent;
    this.difficultNum = difficultNum;
    this.normalNum = normalNum;
    this.easyNum = easyNum;
    this.robotNum = robotNum;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
