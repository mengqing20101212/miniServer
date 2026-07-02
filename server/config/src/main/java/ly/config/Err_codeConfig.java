package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class Err_codeConfig {
  /**id*/
  public final int id;

  /**宏*/
  public final String define;

  /**描述*/
  public final String des;

  /**处理方式*/
  public final int handletype;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public Err_codeConfig(int id, String define, String des, int handletype) {
    this.id = id;
    this.define = define;
    this.des = des;
    this.handletype = handletype;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
