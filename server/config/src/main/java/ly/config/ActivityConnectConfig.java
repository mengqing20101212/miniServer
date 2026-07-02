package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityConnectConfig {
  /**索引ID*/
  public final int id;

  /**阶段*/
  public final int stage;

  /**奖励后端*/
  public final int drop;

  /**奖励前端*/
  public final int dropShow;

  /**对应位置*/
  public final String position;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityConnectConfig(int id, int stage, int drop, int dropShow, String position) {
    this.id = id;
    this.stage = stage;
    this.drop = drop;
    this.dropShow = dropShow;
    this.position = position;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
