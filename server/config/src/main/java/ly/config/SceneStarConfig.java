package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SceneStarConfig {
  /**id*/
  public final int id;

  /**类型*/
  public final int type;

  /**参数x*/
  public final int parax;

  /**参数y*/
  public final int paray;

  /**描述*/
  public final String description;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SceneStarConfig(int id, int type, int parax, int paray, String description) {
    this.id = id;
    this.type = type;
    this.parax = parax;
    this.paray = paray;
    this.description = description;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
