package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SaitamaCardConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String name;

  /**关联道具*/
  public final int itemId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SaitamaCardConfig(int id, String name, int itemId) {
    this.id = id;
    this.name = name;
    this.itemId = itemId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
