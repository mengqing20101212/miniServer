package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class NpcNewInfoConfig {
  /**编号*/
  public final int id;

  /**名字*/
  public final String beizhu;

  /**源核类型*/
  public final String circuitName;

  /**名字*/
  public final String name;

  /**类型*/
  public final int genType;

  /**索引id*/
  public final int checkId;

  /**等级*/
  public final int level;

  /**星级*/
  public final int star;

  /**进阶等级*/
  public final int advance;

  /**觉醒等级*/
  public final int awakenLv;

  /**技能等级*/
  public final String skillLv;

  /**S技能等级*/
  public final String sSkillLv;

  /**源核等级*/
  public final int circuitLv;

  /**源核品质*/
  public final int circuitQuality;

  /**源核模板*/
  public final int circuitInfo;

  /**生命上限系数*/
  public final int maxHPCoe;

  /**攻击系数*/
  public final int attackCoe;

  /**防御系数*/
  public final int defenceCoe;

  /**速度系数*/
  public final int speedCoe;

  /**暴击系数*/
  public final int critCoe;

  /**暴击伤害系数*/
  public final int critRatioCoe;

  /**效果命中系数*/
  public final int effectHitCoe;

  /**效果抵抗系数*/
  public final int effectDodgeCoe;

  /**技能列表*/
  public final String skills;

  /**S技能列表*/
  public final String sSkills;

  /**AI模板*/
  public final String aiName;

  /**是否boss*/
  public final int isBoss;

  /**能否逃跑*/
  public final String canRun;

  /**AI技能CD*/
  public final String extraSkillInfo;

  /**NPC类别*/
  public final int npcType;

  /**NPC标记*/
  public final int entityTags;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public NpcNewInfoConfig(int id, String beizhu, String circuitName, String name, int genType, int checkId, int level, int star, int advance, int awakenLv, String skillLv, String sSkillLv, int circuitLv, int circuitQuality, int circuitInfo, int maxHPCoe, int attackCoe, int defenceCoe, int speedCoe, int critCoe, int critRatioCoe, int effectHitCoe, int effectDodgeCoe, String skills, String sSkills, String aiName, int isBoss, String canRun, String extraSkillInfo, int npcType, int entityTags) {
    this.id = id;
    this.beizhu = beizhu;
    this.circuitName = circuitName;
    this.name = name;
    this.genType = genType;
    this.checkId = checkId;
    this.level = level;
    this.star = star;
    this.advance = advance;
    this.awakenLv = awakenLv;
    this.skillLv = skillLv;
    this.sSkillLv = sSkillLv;
    this.circuitLv = circuitLv;
    this.circuitQuality = circuitQuality;
    this.circuitInfo = circuitInfo;
    this.maxHPCoe = maxHPCoe;
    this.attackCoe = attackCoe;
    this.defenceCoe = defenceCoe;
    this.speedCoe = speedCoe;
    this.critCoe = critCoe;
    this.critRatioCoe = critRatioCoe;
    this.effectHitCoe = effectHitCoe;
    this.effectDodgeCoe = effectDodgeCoe;
    this.skills = skills;
    this.sSkills = sSkills;
    this.aiName = aiName;
    this.isBoss = isBoss;
    this.canRun = canRun;
    this.extraSkillInfo = extraSkillInfo;
    this.npcType = npcType;
    this.entityTags = entityTags;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
