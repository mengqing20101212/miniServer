package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SkillBaseConfig {
  /**技能ID*/
  public final int id;

  /**技能名称*/
  public final String name;

  /**技能描述*/
  public final String description;

  /**升级描述*/
  public final String upgradeDes;

  /**详情*/
  public final String detail;

  /**是否bonus触发图标*/
  public final int bonusTrigger;

  /**技能泡泡字本地化id*/
  public final String popLocId;

  /**是否展示泡泡字*/
  public final int isPopShow;

  /**泡泡字显示类型*/
  public final int popType;

  /**技能图标*/
  public final int icon;

  /**反查技能组id*/
  public final int showSGId;

  /**技能模板id(服务器)*/
  public final int skillBase;

  /**技能模板id*/
  public final int skillGroupId;

  /**互斥类型*/
  public final int mutexType;

  /**互斥优先级*/
  public final int priority;

  /**组*/
  public final int group;

  /**状态中是否触发*/
  public final int isTriggerInState;

  /**转化条件*/
  public final int transType;

  /**转化条件*/
  public final String transCondition;

  /**转化技能*/
  public final String transSkills;

  /**攻击类型*/
  public final int skillTargetType;

  /**能否施法者死亡后释放*/
  public final int canZombie;

  /**是否延迟死亡*/
  public final int isDelayDeath;

  /**是否技能中死亡*/
  public final int isCasterDieInSkill;

  /**是否为普攻*/
  public final int isBasic;

  /**是否为攻击*/
  public final int isAttack;

  /**是否为被动技能*/
  public final int isPassive;

  /**是否为主动技能*/
  public final int isEnergy;

  /**是否为触发技能*/
  public final int isTrigger;

  /**是否为S技能*/
  public final int isSuper;

  /**是否为召唤技能*/
  public final int isSummon;

  /**能量消耗*/
  public final int consumeEnergy;

  /**是否战斗开始生效*/
  public final int isUseAtStart;

  /**冷却时间*/
  public final int cd;

  /**行为树名称*/
  public final String behaviorTreeName;

  /**技能等级*/
  public final int skillLv;

  /**升级物品*/
  public final String upgradeItems;

  /**升级替换物*/
  public final int replaceItems;

  /**标签*/
  public final String flags;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SkillBaseConfig(int id, String name, String description, String upgradeDes, String detail, int bonusTrigger, String popLocId, int isPopShow, int popType, int icon, int showSGId, int skillBase, int skillGroupId, int mutexType, int priority, int group, int isTriggerInState, int transType, String transCondition, String transSkills, int skillTargetType, int canZombie, int isDelayDeath, int isCasterDieInSkill, int isBasic, int isAttack, int isPassive, int isEnergy, int isTrigger, int isSuper, int isSummon, int consumeEnergy, int isUseAtStart, int cd, String behaviorTreeName, int skillLv, String upgradeItems, int replaceItems, String flags) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.upgradeDes = upgradeDes;
    this.detail = detail;
    this.bonusTrigger = bonusTrigger;
    this.popLocId = popLocId;
    this.isPopShow = isPopShow;
    this.popType = popType;
    this.icon = icon;
    this.showSGId = showSGId;
    this.skillBase = skillBase;
    this.skillGroupId = skillGroupId;
    this.mutexType = mutexType;
    this.priority = priority;
    this.group = group;
    this.isTriggerInState = isTriggerInState;
    this.transType = transType;
    this.transCondition = transCondition;
    this.transSkills = transSkills;
    this.skillTargetType = skillTargetType;
    this.canZombie = canZombie;
    this.isDelayDeath = isDelayDeath;
    this.isCasterDieInSkill = isCasterDieInSkill;
    this.isBasic = isBasic;
    this.isAttack = isAttack;
    this.isPassive = isPassive;
    this.isEnergy = isEnergy;
    this.isTrigger = isTrigger;
    this.isSuper = isSuper;
    this.isSummon = isSummon;
    this.consumeEnergy = consumeEnergy;
    this.isUseAtStart = isUseAtStart;
    this.cd = cd;
    this.behaviorTreeName = behaviorTreeName;
    this.skillLv = skillLv;
    this.upgradeItems = upgradeItems;
    this.replaceItems = replaceItems;
    this.flags = flags;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
