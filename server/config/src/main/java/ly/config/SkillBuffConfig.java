package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SkillBuffConfig {
  /**编号*/
  public final int id;

  /**效果类型*/
  public final int effectType;

  /**作用目标类型*/
  public final String targetType;

  /**效果名称*/
  public final String name;

  /**效果描述*/
  public final String description;

  /**效果图标*/
  public final int icon;

  /**源核触发显示id*/
  public final String suitId;

  /**是否显示*/
  public final int display;

  /**显示类型*/
  public final int displayType;

  /**对死亡目标处理*/
  public final int targetTypeEx;

  /**目标属性筛选*/
  public final String targetTypeEx2;

  /**归属技能组*/
  public final int originSkillGroupId;

  /**封被动类型*/
  public final int banPassiveType;

  /**是否为被动*/
  public final int isPassive;

  /**是否为源核*/
  public final int isCircuit;

  /**是否触发源核*/
  public final int isTriggerCircuit;

  /**是否触发被动技能*/
  public final int isTriggerPassiveSkill;

  /**释放者击飞buff能否触发*/
  public final int casterStrikeFlyTriggerFlag;

  /**依附者击飞buff能否触发*/
  public final int targetStrikeFlyTriggerFlag;

  /**依附者击飞buff能否添加*/
  public final int targetStrikeFlyAddFlag;

  /**互斥类型*/
  public final int mutexType;

  /**互斥优先级*/
  public final int mutexPriority;

  /**伤害类型*/
  public final int rangeType;

  /**buff影响*/
  public final String buffInfluence;

  /**初始层数*/
  public final int initStack;

  /**基础层数*/
  public final int baseStack;

  /**最大叠加层数*/
  public final int maxStack;

  /**是否多个共存*/
  public final int coexist;

  /**是否叠层刷新*/
  public final int refreshStack;

  /**效果添加类型*/
  public final int addProType;

  /**效果添加几率*/
  public final int addPro;

  /**效果持续类型*/
  public final int continuousType;

  /**效果持续量*/
  public final String continuousValue;

  /**效果生效时机*/
  public final int triggerType;

  /**生效间隔*/
  public final int triggerInterval;

  /**效果生效几率*/
  public final int triggerPro;

  /**单回合触发上限*/
  public final int triggerLimitPerTurn;

  /**效果生效消耗层数*/
  public final int triggerConsumeStack;

  /**生效标记过滤*/
  public final String entityTagFilters;

  /**效果中断时机*/
  public final String breakType;

  /**中断后续效果几率*/
  public final int breakEffectPro;

  /**中断后续效果*/
  public final String breakEffects;

  /**结束后续效果*/
  public final String endEffects;

  /**结束后续效果*/
  public final String consumeEffects;

  /**s能量获取系数*/
  public final int spCoa1;

  /**s能量获取系数*/
  public final int spCoa2;

  /**效果开始表现*/
  public final String startPerformance;

  /**效果持续表现*/
  public final String runPerformance;

  /**效果中断表现*/
  public final String breakPerformance;

  /**效果未命中表现*/
  public final String missPerformance;

  /**效果结束表现*/
  public final String endPerformance;

  /**触发时长*/
  public final float triggerLength;

  /**效果参数*/
  public final String param_1;

  /**效果参数*/
  public final String param_2;

  /**效果参数*/
  public final String param_3;

  /**效果参数*/
  public final String param_4;

  /**效果参数*/
  public final String param_5;

  /**效果参数*/
  public final String param_6;

  /**效果参数*/
  public final String param_7;

  /**效果参数*/
  public final String param_8;

  /**效果参数*/
  public final String param_9;

  /**效果参数*/
  public final String param_10;

  /**英雄id*/
  public final int heroId;

  /**技能序列*/
  public final int skillSequence;

  /**效果序列*/
  public final int effectSequence;

  /**是否下次战斗保留*/
  public final int isReAdd;

  /**特效恢复信息*/
  public final String effectRenew;

  /**模型展示*/
  public final String modelPerformance;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SkillBuffConfig(int id, int effectType, String targetType, String name, String description, int icon, String suitId, int display, int displayType, int targetTypeEx, String targetTypeEx2, int originSkillGroupId, int banPassiveType, int isPassive, int isCircuit, int isTriggerCircuit, int isTriggerPassiveSkill, int casterStrikeFlyTriggerFlag, int targetStrikeFlyTriggerFlag, int targetStrikeFlyAddFlag, int mutexType, int mutexPriority, int rangeType, String buffInfluence, int initStack, int baseStack, int maxStack, int coexist, int refreshStack, int addProType, int addPro, int continuousType, String continuousValue, int triggerType, int triggerInterval, int triggerPro, int triggerLimitPerTurn, int triggerConsumeStack, String entityTagFilters, String breakType, int breakEffectPro, String breakEffects, String endEffects, String consumeEffects, int spCoa1, int spCoa2, String startPerformance, String runPerformance, String breakPerformance, String missPerformance, String endPerformance, float triggerLength, String param_1, String param_2, String param_3, String param_4, String param_5, String param_6, String param_7, String param_8, String param_9, String param_10, int heroId, int skillSequence, int effectSequence, int isReAdd, String effectRenew, String modelPerformance) {
    this.id = id;
    this.effectType = effectType;
    this.targetType = targetType;
    this.name = name;
    this.description = description;
    this.icon = icon;
    this.suitId = suitId;
    this.display = display;
    this.displayType = displayType;
    this.targetTypeEx = targetTypeEx;
    this.targetTypeEx2 = targetTypeEx2;
    this.originSkillGroupId = originSkillGroupId;
    this.banPassiveType = banPassiveType;
    this.isPassive = isPassive;
    this.isCircuit = isCircuit;
    this.isTriggerCircuit = isTriggerCircuit;
    this.isTriggerPassiveSkill = isTriggerPassiveSkill;
    this.casterStrikeFlyTriggerFlag = casterStrikeFlyTriggerFlag;
    this.targetStrikeFlyTriggerFlag = targetStrikeFlyTriggerFlag;
    this.targetStrikeFlyAddFlag = targetStrikeFlyAddFlag;
    this.mutexType = mutexType;
    this.mutexPriority = mutexPriority;
    this.rangeType = rangeType;
    this.buffInfluence = buffInfluence;
    this.initStack = initStack;
    this.baseStack = baseStack;
    this.maxStack = maxStack;
    this.coexist = coexist;
    this.refreshStack = refreshStack;
    this.addProType = addProType;
    this.addPro = addPro;
    this.continuousType = continuousType;
    this.continuousValue = continuousValue;
    this.triggerType = triggerType;
    this.triggerInterval = triggerInterval;
    this.triggerPro = triggerPro;
    this.triggerLimitPerTurn = triggerLimitPerTurn;
    this.triggerConsumeStack = triggerConsumeStack;
    this.entityTagFilters = entityTagFilters;
    this.breakType = breakType;
    this.breakEffectPro = breakEffectPro;
    this.breakEffects = breakEffects;
    this.endEffects = endEffects;
    this.consumeEffects = consumeEffects;
    this.spCoa1 = spCoa1;
    this.spCoa2 = spCoa2;
    this.startPerformance = startPerformance;
    this.runPerformance = runPerformance;
    this.breakPerformance = breakPerformance;
    this.missPerformance = missPerformance;
    this.endPerformance = endPerformance;
    this.triggerLength = triggerLength;
    this.param_1 = param_1;
    this.param_2 = param_2;
    this.param_3 = param_3;
    this.param_4 = param_4;
    this.param_5 = param_5;
    this.param_6 = param_6;
    this.param_7 = param_7;
    this.param_8 = param_8;
    this.param_9 = param_9;
    this.param_10 = param_10;
    this.heroId = heroId;
    this.skillSequence = skillSequence;
    this.effectSequence = effectSequence;
    this.isReAdd = isReAdd;
    this.effectRenew = effectRenew;
    this.modelPerformance = modelPerformance;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
