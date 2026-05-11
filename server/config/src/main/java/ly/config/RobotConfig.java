package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class RobotConfig {
  /**编号*/
  public final int id;

  /**敌人数值信息*/
  public final String enemyInfo;

  /**敌人源核信息*/
  public final String enemyCircuitInfo;

  /**敌人系数*/
  public final String enemyPara;

  /**npcid*/
  public final String npcId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public RobotConfig(int id, String enemyInfo, String enemyCircuitInfo, String enemyPara, String npcId) {
    this.id = id;
    this.enemyInfo = enemyInfo;
    this.enemyCircuitInfo = enemyCircuitInfo;
    this.enemyPara = enemyPara;
    this.npcId = npcId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
