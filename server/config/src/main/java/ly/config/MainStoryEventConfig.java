package ly.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class MainStoryEventConfig { 
  /**ID（event唯一id）*/ 
   public int id;

  /**名称(备注)*/ 
   public String name;

  /**事件类型*/ 
   public int type;

  /**参数*/ 
   public String values;

  /**事件节点组*/ 
   public int group;

  /**场景( 目前每章都是一个场景)*/ 
   public String sceneResource;

  /**战斗失败后能否继续往下读取事件*/ 
   public int loseContinue;

  /**转场类型*/ 
   public int transitionType;

  /**是否预加载(只支持对话并且连续)*/ 
   public int isPreload;

// @@@@@自定义属性开始区@@@@@ 

 // @@@@@自定义属性结束区@@@@@ 

// @@@@@自定义方法开始区@@@@@ 
public void afterLoad() {}


 // @@@@@自定义方法结束区@@@@@ 

 }
