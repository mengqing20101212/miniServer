package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ValueConfig {
  /**编号*/
  public final int id;

  /**值*/
  public final int values;

  /**描述*/
  public final String description;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ValueConfig(int id, int values, String description) {
    this.id = id;
    this.values = values;
    this.description = description;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
