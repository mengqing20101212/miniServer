package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class AdvancedTypeConfig {
  /**编号*/
  public final int id;

  /**类型列表*/
  public final String stageTypeList;

  /**活动表ID*/
  public final int activityId;

  /**名字*/
  public final String nameList;

  /**图标*/
  public final String iconList;

  /**外框*/
  public final String outlineList;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public AdvancedTypeConfig(int id, String stageTypeList, int activityId, String nameList, String iconList, String outlineList) {
    this.id = id;
    this.stageTypeList = stageTypeList;
    this.activityId = activityId;
    this.nameList = nameList;
    this.iconList = iconList;
    this.outlineList = outlineList;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
