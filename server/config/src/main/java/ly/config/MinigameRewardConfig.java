package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class MinigameRewardConfig {
  /**编号*/
  public final int id;

  /**备注*/
  public final String name;

  /**需要分数*/
  public final String needScore;

  /**奖励*/
  public final int dropId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public MinigameRewardConfig(int id, String name, String needScore, int dropId) {
    this.id = id;
    this.name = name;
    this.needScore = needScore;
    this.dropId = dropId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
