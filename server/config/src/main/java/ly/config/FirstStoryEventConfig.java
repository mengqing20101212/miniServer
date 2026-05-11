package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class FirstStoryEventConfig {
  /**ID（event唯一id）*/
  public final int id;

  /**名称(备注)*/
  public final String name;

  /**事件类型*/
  public final int type;

  /**参数*/
  public final String values;

  /**战斗前后*/
  public final int flag;

  /**转场类型*/
  public final int transitionType;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public FirstStoryEventConfig(int id, String name, int type, String values, int flag, int transitionType) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.values = values;
    this.flag = flag;
    this.transitionType = transitionType;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
