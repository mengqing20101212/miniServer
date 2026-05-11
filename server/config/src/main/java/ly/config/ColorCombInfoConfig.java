package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ColorCombInfoConfig {
  /**组合编号*/
  public final int id;

  /**颜色组id*/
  public final int groupId;

  /**次序*/
  public final int squence;

  /**颜色数量*/
  public final String condition;

  /**属性Id*/
  public final int attrId;

  /**是否有开关*/
  public final int isSwitch;

  /**备注*/
  public final String des;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ColorCombInfoConfig(int id, int groupId, int squence, String condition, int attrId, int isSwitch, String des) {
    this.id = id;
    this.groupId = groupId;
    this.squence = squence;
    this.condition = condition;
    this.attrId = attrId;
    this.isSwitch = isSwitch;
    this.des = des;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
