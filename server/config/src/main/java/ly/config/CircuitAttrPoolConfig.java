package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class CircuitAttrPoolConfig {
  /**编号*/
  public final int id;

  /**备注*/
  public final String des;

  /**属性列表*/
  public final String attrList;

  /**组概率(绝对概率)*/
  public final String attrPro;

  /**组内概率(相对概率_特殊)*/
  public final String attrRelativePro;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public CircuitAttrPoolConfig(int id, String des, String attrList, String attrPro, String attrRelativePro) {
    this.id = id;
    this.des = des;
    this.attrList = attrList;
    this.attrPro = attrPro;
    this.attrRelativePro = attrRelativePro;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
