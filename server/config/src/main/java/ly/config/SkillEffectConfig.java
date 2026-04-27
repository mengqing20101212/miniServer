package ly.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SkillEffectConfig { 
  /**编号*/ 
   public int id;

  /**效果类型*/ 
   public int effectType;

  /**作用目标类型*/ 
   public String targetType;

  /**效果名称*/ 
   public String name;

  /**效果描述*/ 
   public String description;

  /**是否触发源核*/ 
   public int isTriggerCircuit;

  /**是否触发被动技能*/ 
   public int isTriggerPassiveSkill;

  /**释放者击飞buff能否触发*/ 
   public int casterStrikeFlyTriggerFlag;

  /**依附者击飞buff能否触发*/ 
   public int targetStrikeFlyTriggerFlag;

  /**对死亡目标处理*/ 
   public int targetTypeEx;

  /**目标属性筛选*/ 
   public String targetTypeEx2;

  /**伤害类型*/ 
   public int rangeType;

  /**buff影响*/ 
   public String buffInfluence;

  /**效果添加类型*/ 
   public int addProType;

  /**效果添加几率*/ 
   public int addPro;

  /**生效标记过滤*/ 
   public String entityTagFilters;

  /**结束后续效果*/ 
   public String endEffects;

  /**s能量获取系数*/ 
   public int spCoa1;

  /**s能量获取系数*/ 
   public int spCoa2;

  /**效果开始表现*/ 
   public String startPerformance;

  /**效果持续表现*/ 
   public String runPerformance;

  /**效果未命中表现*/ 
   public String missPerformance;

  /**效果结束表现*/ 
   public String endPerformance;

  /**效果参数*/ 
   public String param_1;

  /**效果参数*/ 
   public String param_2;

  /**效果参数*/ 
   public String param_3;

  /**效果参数*/ 
   public String param_4;

  /**效果参数*/ 
   public String param_5;

  /**效果参数*/ 
   public String param_6;

  /**效果参数*/ 
   public String param_7;

  /**效果参数*/ 
   public String param_8;

  /**效果参数*/ 
   public String param_9;

  /**效果参数*/ 
   public String param_10;

  /**英雄id*/ 
   public int heroId;

  /**技能序列*/ 
   public int skillSequence;

  /**效果序列*/ 
   public int effectSequence;

  /**特效恢复信息*/ 
   public String effectRenew;

// @@@@@自定义属性开始区@@@@@ 

 // @@@@@自定义属性结束区@@@@@ 

// @@@@@自定义方法开始区@@@@@ 
public void afterLoad() {}


 // @@@@@自定义方法结束区@@@@@ 

 }
