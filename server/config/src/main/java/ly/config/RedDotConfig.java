package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class RedDotConfig {
  /**编号*/
  public final int id;

  /**开关*/
  public final int SWITCH;

  /**系统编号*/
  public final int number;

  /**类型*/
  public final int type;

  /**父节点*/
  public final int father;

  /**子界面*/
  public final String subclass;

  /**描述*/
  public final String des;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public RedDotConfig(int id, int SWITCH, int number, int type, int father, String subclass, String des) {
    this.id = id;
    this.SWITCH = SWITCH;
    this.number = number;
    this.type = type;
    this.father = father;
    this.subclass = subclass;
    this.des = des;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
