package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SkillEffectConfig {
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

  /**是否触发源核*/
  public final int isTriggerCircuit;

  /**是否触发被动技能*/
  public final int isTriggerPassiveSkill;

  /**释放者击飞buff能否触发*/
  public final int casterStrikeFlyTriggerFlag;

  /**依附者击飞buff能否触发*/
  public final int targetStrikeFlyTriggerFlag;

  /**对死亡目标处理*/
  public final int targetTypeEx;

  /**目标属性筛选*/
  public final String targetTypeEx2;

  /**伤害类型*/
  public final int rangeType;

  /**buff影响*/
  public final String buffInfluence;

  /**效果添加类型*/
  public final int addProType;

  /**效果添加几率*/
  public final int addPro;

  /**生效标记过滤*/
  public final String entityTagFilters;

  /**结束后续效果*/
  public final String endEffects;

  /**s能量获取系数*/
  public final int spCoa1;

  /**s能量获取系数*/
  public final int spCoa2;

  /**效果开始表现*/
  public final String startPerformance;

  /**效果持续表现*/
  public final String runPerformance;

  /**效果未命中表现*/
  public final String missPerformance;

  /**效果结束表现*/
  public final String endPerformance;

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

  /**特效恢复信息*/
  public final String effectRenew;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SkillEffectConfig(int id, int effectType, String targetType, String name, String description, int isTriggerCircuit, int isTriggerPassiveSkill, int casterStrikeFlyTriggerFlag, int targetStrikeFlyTriggerFlag, int targetTypeEx, String targetTypeEx2, int rangeType, String buffInfluence, int addProType, int addPro, String entityTagFilters, String endEffects, int spCoa1, int spCoa2, String startPerformance, String runPerformance, String missPerformance, String endPerformance, String param_1, String param_2, String param_3, String param_4, String param_5, String param_6, String param_7, String param_8, String param_9, String param_10, int heroId, int skillSequence, int effectSequence, String effectRenew) {
    this.id = id;
    this.effectType = effectType;
    this.targetType = targetType;
    this.name = name;
    this.description = description;
    this.isTriggerCircuit = isTriggerCircuit;
    this.isTriggerPassiveSkill = isTriggerPassiveSkill;
    this.casterStrikeFlyTriggerFlag = casterStrikeFlyTriggerFlag;
    this.targetStrikeFlyTriggerFlag = targetStrikeFlyTriggerFlag;
    this.targetTypeEx = targetTypeEx;
    this.targetTypeEx2 = targetTypeEx2;
    this.rangeType = rangeType;
    this.buffInfluence = buffInfluence;
    this.addProType = addProType;
    this.addPro = addPro;
    this.entityTagFilters = entityTagFilters;
    this.endEffects = endEffects;
    this.spCoa1 = spCoa1;
    this.spCoa2 = spCoa2;
    this.startPerformance = startPerformance;
    this.runPerformance = runPerformance;
    this.missPerformance = missPerformance;
    this.endPerformance = endPerformance;
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
    this.effectRenew = effectRenew;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
