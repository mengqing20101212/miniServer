package ly.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SkillBuffConfig { 
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

  /**效果图标*/ 
   public int icon;

  /**源核触发显示id*/ 
   public String suitId;

  /**是否显示*/ 
   public int display;

  /**显示类型*/ 
   public int displayType;

  /**对死亡目标处理*/ 
   public int targetTypeEx;

  /**目标属性筛选*/ 
   public String targetTypeEx2;

  /**归属技能组*/ 
   public int originSkillGroupId;

  /**封被动类型*/ 
   public int banPassiveType;

  /**是否为被动*/ 
   public int isPassive;

  /**是否为源核*/ 
   public int isCircuit;

  /**是否触发源核*/ 
   public int isTriggerCircuit;

  /**是否触发被动技能*/ 
   public int isTriggerPassiveSkill;

  /**释放者击飞buff能否触发*/ 
   public int casterStrikeFlyTriggerFlag;

  /**依附者击飞buff能否触发*/ 
   public int targetStrikeFlyTriggerFlag;

  /**依附者击飞buff能否添加*/ 
   public int targetStrikeFlyAddFlag;

  /**互斥类型*/ 
   public int mutexType;

  /**互斥优先级*/ 
   public int mutexPriority;

  /**伤害类型*/ 
   public int rangeType;

  /**buff影响*/ 
   public String buffInfluence;

  /**初始层数*/ 
   public int initStack;

  /**基础层数*/ 
   public int baseStack;

  /**最大叠加层数*/ 
   public int maxStack;

  /**是否多个共存*/ 
   public int coexist;

  /**是否叠层刷新*/ 
   public int refreshStack;

  /**效果添加类型*/ 
   public int addProType;

  /**效果添加几率*/ 
   public int addPro;

  /**效果持续类型*/ 
   public int continuousType;

  /**效果持续量*/ 
   public String continuousValue;

  /**效果生效时机*/ 
   public int triggerType;

  /**生效间隔*/ 
   public int triggerInterval;

  /**效果生效几率*/ 
   public int triggerPro;

  /**单回合触发上限*/ 
   public int triggerLimitPerTurn;

  /**效果生效消耗层数*/ 
   public int triggerConsumeStack;

  /**生效标记过滤*/ 
   public String entityTagFilters;

  /**效果中断时机*/ 
   public String breakType;

  /**中断后续效果几率*/ 
   public int breakEffectPro;

  /**中断后续效果*/ 
   public String breakEffects;

  /**结束后续效果*/ 
   public String endEffects;

  /**结束后续效果*/ 
   public String consumeEffects;

  /**s能量获取系数*/ 
   public int spCoa1;

  /**s能量获取系数*/ 
   public int spCoa2;

  /**效果开始表现*/ 
   public String startPerformance;

  /**效果持续表现*/ 
   public String runPerformance;

  /**效果中断表现*/ 
   public String breakPerformance;

  /**效果未命中表现*/ 
   public String missPerformance;

  /**效果结束表现*/ 
   public String endPerformance;

  /**触发时长*/ 
   public float triggerLength;

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

  /**是否下次战斗保留*/ 
   public int isReAdd;

  /**特效恢复信息*/ 
   public String effectRenew;

  /**模型展示*/ 
   public String modelPerformance;

// @@@@@自定义属性开始区@@@@@ 

 // @@@@@自定义属性结束区@@@@@ 

// @@@@@自定义方法开始区@@@@@ 
public void afterLoad() {}


 // @@@@@自定义方法结束区@@@@@ 

 }
