package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ManualCircuitConfig {
  public final int id;

  public final int taskId;

  public final String front;

  public final java.util.List<Integer> oriPos;

  public final java.util.List<Integer> type;

  public final String redirectionIdDes;

  public final int redirectionId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ManualCircuitConfig(int id, int taskId, String front, java.util.List<Integer> oriPos, java.util.List<Integer> type, String redirectionIdDes, int redirectionId) {
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
