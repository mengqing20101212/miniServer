package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DropInclinationConfig {
  /**物品ID*/
  public final int id;

  /**转换ID*/
  public final int changeId;

  /**关卡类型*/
  public final int inclination;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DropInclinationConfig(int id, int changeId, int inclination) {
    this.id = id;
    this.changeId = changeId;
    this.inclination = inclination;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
