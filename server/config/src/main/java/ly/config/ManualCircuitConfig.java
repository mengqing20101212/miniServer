package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ManualCircuitConfig {
  /**编号*/
  public final int id;

  /**任务ID*/
  public final int taskId;

  /**前置档位*/
  public final String front;

  /**任务坐标*/
  public final String oriPos;

  /**折点样式*/
  public final String type;

  /**跳转描述*/
  public final String redirectionIdDes;

  /**跳转*/
  public final int redirectionId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ManualCircuitConfig(int id, int taskId, String front, String oriPos, String type, String redirectionIdDes, int redirectionId) {
    this.id = id;
    this.taskId = taskId;
    this.front = front;
    this.oriPos = oriPos;
    this.type = type;
    this.redirectionIdDes = redirectionIdDes;
    this.redirectionId = redirectionId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
