package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class CoordinationConfig {
  /**协同位置*/
  public final int position;

  /**等级解锁*/
  public final int levelUnlock;

  /**协同值解锁*/
  public final int coordinationUnlock;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public CoordinationConfig(int position, int levelUnlock, int coordinationUnlock) {
    this.position = position;
    this.levelUnlock = levelUnlock;
    this.coordinationUnlock = coordinationUnlock;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
