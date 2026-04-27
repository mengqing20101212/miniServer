package ly.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class AbnormalStateConfig { 
  /**状态编号*/ 
   public int id;

  /**状态名*/ 
   public String name;

  /**类型*/ 
   public int type;

  /**类型功能是否触发多次*/ 
   public int canReTrigger;

  /**进入状态后是否播其他动作*/ 
   public int playAni;

  /**进入状态后是否播展示动作*/ 
   public int playShowAni;

  /**跳过回合*/ 
   public int skipTurn;

  /**是否封主动*/ 
   public int banEnergySkill;

  /**是否封被动*/ 
   public int banPassiveSkill;

  /**是否封S技*/ 
   public int banSSkill;

  /**进入状态后是否触发技能*/ 
   public int canTriggerSkill;

  /**进入状态后选择类型*/ 
   public int selectType;

  /**驱散状态列表*/ 
   public String dispelList;

  /**阻止状态列表*/ 
   public String preventList;

  /**是否有状态动作*/ 
   public int isPlayAnim;

  /**状态待机*/ 
   public String stateAnim;

  /**状态死亡*/ 
   public String stateDead;

  /**开始动作*/ 
   public String stateStart;

  /**结束动作*/ 
   public String stateFinish;

  /**开始特效*/ 
   public String startEffect;

  /**持续特效*/ 
   public String runEffect;

  /**结束特效*/ 
   public String endEffect;

  /**状态优先级*/ 
   public int statePriority;

  /**伤害命中加成*/ 
   public int damageHitPro;

// @@@@@自定义属性开始区@@@@@ 

 // @@@@@自定义属性结束区@@@@@ 

// @@@@@自定义方法开始区@@@@@ 
public void afterLoad() {}


 // @@@@@自定义方法结束区@@@@@ 

 }
