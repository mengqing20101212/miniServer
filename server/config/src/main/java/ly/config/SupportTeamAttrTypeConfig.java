package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SupportTeamAttrTypeConfig {
  /**属性类型*/
  public final int attrType;

  /**属性功能*/
  public final String function;

  /**显示*/
  public final String des;

  /**图标id*/
  public final int icon;

  /**是否百分比*/
  public final int isPercent;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SupportTeamAttrTypeConfig(int attrType, String function, String des, int icon, int isPercent) {
    this.attrType = attrType;
    this.function = function;
    this.des = des;
    this.icon = icon;
    this.isPercent = isPercent;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
