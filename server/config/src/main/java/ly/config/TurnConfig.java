package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class TurnConfig {
  /**编号*/
  public final int id;

  /**描述*/
  public final String description;

  /**类型*/
  public final int turnType;

  /**打开的uimsg*/
  public final String uiMsg;

  /**类型*/
  public final int type;

  /**类型2*/
  public final int type2;

  /**是否要前置消息*/
  public final int isNet;

  /**参数*/
  public final String param;

  /**参数2()目前没用*/
  public final String param2;

  /**限制活动id*/
  public final int activityId;

  /**引导组ID*/
  public final int guideId;

  /**引导未完成打开的uimsg*/
  public final String frontUiMsg;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public TurnConfig(int id, String description, int turnType, String uiMsg, int type, int type2, int isNet, String param, String param2, int activityId, int guideId, String frontUiMsg) {
    this.id = id;
    this.description = description;
    this.turnType = turnType;
    this.uiMsg = uiMsg;
    this.type = type;
    this.type2 = type2;
    this.isNet = isNet;
    this.param = param;
    this.param2 = param2;
    this.activityId = activityId;
    this.guideId = guideId;
    this.frontUiMsg = frontUiMsg;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
