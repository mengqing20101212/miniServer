package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class CheckPointConfig {
  /**编号*/
  public final int id;

  /**备注*/
  public final String des;

  /**打点类型*/
  public final int pointType;

  /**参数*/
  public final String para;

  /**特殊功能*/
  public final String functionName;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public CheckPointConfig(int id, String des, int pointType, String para, String functionName) {
    this.id = id;
    this.des = des;
    this.pointType = pointType;
    this.para = para;
    this.functionName = functionName;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
