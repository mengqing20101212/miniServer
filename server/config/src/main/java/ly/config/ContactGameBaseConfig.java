package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ContactGameBaseConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String name;

  /**资源id*/
  public final int resource;

  /**资源id*/
  public final String tag;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ContactGameBaseConfig(int id, String name, int resource, String tag) {
    this.id = id;
    this.name = name;
    this.resource = resource;
    this.tag = tag;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
