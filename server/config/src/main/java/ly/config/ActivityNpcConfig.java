package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityNpcConfig {
  /**编号*/
  public final int id;

  /**配置名字辅助列*/
  public final String config_name;

  /**活动名称*/
  public final String characterModelId;

  /**默认动作*/
  public final String defaultAni;

  /**欢迎动作*/
  public final String bornShowAni;

  /**点击反馈类型(0-不可点，1-通用点击逻辑，2-特殊*/
  public final String clickType;

  /**点击反馈动作组*/
  public final String clickAniList;

  /**点击反馈镜头组*/
  public final String clickCameraList;

  /**推镜的镜头左右偏移（正是左负是右,左右是角度，上下是距离）*/
  public final String moveDistance;

  /**推镜的距离配置*/
  public final String cameraDistance;

  /**点击反馈文本*/
  public final String clickText;

  /**组合动作的文本*/
  public final String groupText;

  /**特殊功能类型*/
  public final String ActivityType;

  /**特殊功能参数1*/
  public final String param_1;

  /**特殊功能参数2*/
  public final String param_2;

  /**角色组合id*/
  public final String npcGrounpId;

  /**主城显示优先级*/
  public final String showPriority;

  /**装饰物资源id*/
  public final String decorationId;

  /**资源物挂点*/
  public final String decorationPoint;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityNpcConfig(int id, String config_name, String characterModelId, String defaultAni, String bornShowAni, String clickType, String clickAniList, String clickCameraList, String moveDistance, String cameraDistance, String clickText, String groupText, String ActivityType, String param_1, String param_2, String npcGrounpId, String showPriority, String decorationId, String decorationPoint) {
    this.id = id;
    this.config_name = config_name;
    this.characterModelId = characterModelId;
    this.defaultAni = defaultAni;
    this.bornShowAni = bornShowAni;
    this.clickType = clickType;
    this.clickAniList = clickAniList;
    this.clickCameraList = clickCameraList;
    this.moveDistance = moveDistance;
    this.cameraDistance = cameraDistance;
    this.clickText = clickText;
    this.groupText = groupText;
    this.ActivityType = ActivityType;
    this.param_1 = param_1;
    this.param_2 = param_2;
    this.npcGrounpId = npcGrounpId;
    this.showPriority = showPriority;
    this.decorationId = decorationId;
    this.decorationPoint = decorationPoint;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
