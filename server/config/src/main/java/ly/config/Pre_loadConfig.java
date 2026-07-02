package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class Pre_loadConfig {
  /**自增id*/
  public final int id;

  /**描述*/
  public final String desc;

  /**资源id*/
  public final int resid;

  /**资源类型*/
  public final int restype;

  /**预加载触发类型*/
  public final int type;

  /**常驻类型*/
  public final int holdstate;

  /**参数*/
  public final String param;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public Pre_loadConfig(int id, String desc, int resid, int restype, int type, int holdstate, String param) {
    this.id = id;
    this.desc = desc;
    this.resid = resid;
    this.restype = restype;
    this.type = type;
    this.holdstate = holdstate;
    this.param = param;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
