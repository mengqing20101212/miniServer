package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PlayerLvConfig {
  /**编号*/
  public final int id;

  /**掉落奖励*/
  public final int dropId;

  /**提示id*/
  public final String hintId;

  /**体力上限*/
  public final int addStamina;

  /**联络玩法总次数*/
  public final int trialTimes;

  /**升级奖励展示*/
  public final int dropShow;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PlayerLvConfig(int id, int dropId, String hintId, int addStamina, int trialTimes, int dropShow) {
    this.id = id;
    this.dropId = dropId;
    this.hintId = hintId;
    this.addStamina = addStamina;
    this.trialTimes = trialTimes;
    this.dropShow = dropShow;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
