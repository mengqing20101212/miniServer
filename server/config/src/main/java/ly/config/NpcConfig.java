package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class NpcConfig {
  /**编号*/
  public final int id;

  /**名字*/
  public final String beizhu;

  /**名字*/
  public final String name;

  /**等级*/
  public final int level;

  /**星级*/
  public final int star;

  /**生命上限*/
  public final int maxHP;

  /**攻击*/
  public final int attack;

  /**防御*/
  public final int defence;

  /**速度*/
  public final int speed;

  /**暴击*/
  public final int crit;

  /**暴击伤害*/
  public final int critRatio;

  /**效果命中*/
  public final int effectHit;

  /**效果抵抗*/
  public final int effectDodge;

  /**回能*/
  public final int spCoe;

  /**常规技能*/
  public final int skill_1;

  /**  null*/
  public final int skill_2;

  /**  null*/
  public final int skill_3;

  /**超级技能*/
  public final int skill_s1;

  /**超级技能*/
  public final int skill_s2;

  /**AI模板*/
  public final String aiName;

  /**模型id*/
  public final int modelId;

  /**右侧头像*/
  public final int headResource_3;

  /**立绘头像*/
  public final int headResource_4;

  /**s技能立绘切割坐标*/
  public final String sSkillCutUp;

  /**是否boss*/
  public final int isBoss;

  /**能否逃跑*/
  public final String canRun;

  /**关联hero*/
  public final int relateId;

  /**AI技能CD*/
  public final String extraSkillInfo;

  /**是否强制使用AI技能CD*/
  public final int forcedAICD;

  /**技能列表*/
  public final String skills;

  /**S技能列表*/
  public final String sSkills;

  /**NPC类别*/
  public final int npcType;

  /**NPC标记*/
  public final int entityTags;

  /**s技能跳过立绘切割坐标*/
  public final String sSkipCutUp;

  /**变色类别*/
  public final String changeColorInfo;

  /**颜色类型*/
  public final int colorType;

  /**菲尼尔颜色*/
  public final String ShaderFresnel;

  /**英雄类型*/
  public final int heroType;

  /**品质*/
  public final int quality;

  /**角色类别*/
  public final int characterType;

  /**觉醒等级*/
  public final int awakenLv;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public NpcConfig(int id, String beizhu, String name, int level, int star, int maxHP, int attack, int defence, int speed, int crit, int critRatio, int effectHit, int effectDodge, int spCoe, int skill_1, int skill_2, int skill_3, int skill_s1, int skill_s2, String aiName, int modelId, int headResource_3, int headResource_4, String sSkillCutUp, int isBoss, String canRun, int relateId, String extraSkillInfo, int forcedAICD, String skills, String sSkills, int npcType, int entityTags, String sSkipCutUp, String changeColorInfo, int colorType, String ShaderFresnel, int heroType, int quality, int characterType, int awakenLv) {
    this.id = id;
    this.beizhu = beizhu;
    this.name = name;
    this.level = level;
    this.star = star;
    this.maxHP = maxHP;
    this.attack = attack;
    this.defence = defence;
    this.speed = speed;
    this.crit = crit;
    this.critRatio = critRatio;
    this.effectHit = effectHit;
    this.effectDodge = effectDodge;
    this.spCoe = spCoe;
    this.skill_1 = skill_1;
    this.skill_2 = skill_2;
    this.skill_3 = skill_3;
    this.skill_s1 = skill_s1;
    this.skill_s2 = skill_s2;
    this.aiName = aiName;
    this.modelId = modelId;
    this.headResource_3 = headResource_3;
    this.headResource_4 = headResource_4;
    this.sSkillCutUp = sSkillCutUp;
    this.isBoss = isBoss;
    this.canRun = canRun;
    this.relateId = relateId;
    this.extraSkillInfo = extraSkillInfo;
    this.forcedAICD = forcedAICD;
    this.skills = skills;
    this.sSkills = sSkills;
    this.npcType = npcType;
    this.entityTags = entityTags;
    this.sSkipCutUp = sSkipCutUp;
    this.changeColorInfo = changeColorInfo;
    this.colorType = colorType;
    this.ShaderFresnel = ShaderFresnel;
    this.heroType = heroType;
    this.quality = quality;
    this.characterType = characterType;
    this.awakenLv = awakenLv;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
