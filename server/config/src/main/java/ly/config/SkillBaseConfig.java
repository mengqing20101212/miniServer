package ly.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SkillBaseConfig { 
  /**技能ID*/ 
   public int id;

  /**技能名称*/ 
   public String name;

  /**技能描述*/ 
   public String description;

  /**升级描述*/ 
   public String upgradeDes;

  /**详情*/ 
   public String detail;

  /**是否bonus触发图标*/ 
   public int bonusTrigger;

  /**技能泡泡字本地化id*/ 
   public String popLocId;

  /**是否展示泡泡字*/ 
   public int isPopShow;

  /**泡泡字显示类型*/ 
   public int popType;

  /**技能图标*/ 
   public int icon;

  /**反查技能组id*/ 
   public int showSGId;

  /**技能模板id(服务器)*/ 
   public int skillBase;

  /**技能模板id*/ 
   public int skillGroupId;

  /**互斥类型*/ 
   public int mutexType;

  /**互斥优先级*/ 
   public int priority;

  /**组*/ 
   public int group;

  /**状态中是否触发*/ 
   public int isTriggerInState;

  /**转化条件*/ 
   public int transType;

  /**转化条件*/ 
   public String transCondition;

  /**转化技能*/ 
   public String transSkills;

  /**攻击类型*/ 
   public int skillTargetType;

  /**能否施法者死亡后释放*/ 
   public int canZombie;

  /**是否延迟死亡*/ 
   public int isDelayDeath;

  /**是否技能中死亡*/ 
   public int isCasterDieInSkill;

  /**是否为普攻*/ 
   public int isBasic;

  /**是否为攻击*/ 
   public int isAttack;

  /**是否为被动技能*/ 
   public int isPassive;

  /**是否为主动技能*/ 
   public int isEnergy;

  /**是否为触发技能*/ 
   public int isTrigger;

  /**是否为S技能*/ 
   public int isSuper;

  /**是否为召唤技能*/ 
   public int isSummon;

  /**能量消耗*/ 
   public int consumeEnergy;

  /**是否战斗开始生效*/ 
   public int isUseAtStart;

  /**冷却时间*/ 
   public int cd;

  /**行为树名称*/ 
   public String behaviorTreeName;

  /**技能等级*/ 
   public int skillLv;

  /**升级物品*/ 
   public String upgradeItems;

  /**升级替换物*/ 
   public int replaceItems;

  /**标签*/ 
   public String flags;

// @@@@@自定义属性开始区@@@@@ 

 // @@@@@自定义属性结束区@@@@@ 

// @@@@@自定义方法开始区@@@@@ 
public void afterLoad() {}


 // @@@@@自定义方法结束区@@@@@ 

 }
