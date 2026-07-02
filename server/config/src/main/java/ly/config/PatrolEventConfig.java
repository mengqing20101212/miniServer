package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PatrolEventConfig {
  /**id*/
  public final int id;

  /**道具奖励*/
  public final int itemGroupId;

  /**事件结果*/
  public final int eventResult;

  /**描述参数*/
  public final int param;

  /**时间描述*/
  public final String desc;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PatrolEventConfig(int id, int itemGroupId, int eventResult, int param, String desc) {
    this.id = id;
    this.itemGroupId = itemGroupId;
    this.eventResult = eventResult;
    this.param = param;
    this.desc = desc;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
