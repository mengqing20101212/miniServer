package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class TaskBarMainConfig {
  /**ID（任务唯一id）*/
  public final int id;

  /**类型*/
  public final int type;

  /**参数*/
  public final int parameter;

  /**背景图*/
  public final int backGround;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public TaskBarMainConfig(int id, int type, int parameter, int backGround) {
    this.id = id;
    this.type = type;
    this.parameter = parameter;
    this.backGround = backGround;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
