package ly.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityNpcConfig { 
  /**编号*/ 
   public int id;

  /**配置名字辅助列*/ 
   public String config_name;

  /**活动名称*/ 
   public String characterModelId;

  /**默认动作*/ 
   public String defaultAni;

  /**欢迎动作*/ 
   public String bornShowAni;

  /**点击反馈类型(0-不可点，1-通用点击逻辑，2-特殊*/ 
   public String clickType;

  /**点击反馈动作组*/ 
   public String clickAniList;

  /**点击反馈镜头组*/ 
   public String clickCameraList;

  /**推镜的镜头左右偏移（正是左负是右,左右是角度，上下是距离）*/ 
   public String moveDistance;

  /**推镜的距离配置*/ 
   public String cameraDistance;

  /**点击反馈文本*/ 
   public String clickText;

  /**组合动作的文本*/ 
   public String groupText;

  /**特殊功能类型*/ 
   public String ActivityType;

  /**特殊功能参数1*/ 
   public String param_1;

  /**特殊功能参数2*/ 
   public String param_2;

  /**角色组合id*/ 
   public String npcGrounpId;

  /**主城显示优先级*/ 
   public String showPriority;

  /**装饰物资源id*/ 
   public String decorationId;

  /**资源物挂点*/ 
   public String decorationPoint;

// @@@@@自定义属性开始区@@@@@ 

 // @@@@@自定义属性结束区@@@@@ 

// @@@@@自定义方法开始区@@@@@ 
public void afterLoad() {}


 // @@@@@自定义方法结束区@@@@@ 

 }
