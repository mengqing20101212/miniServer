package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class AbnormalStateConfig {
  /**状态编号*/
  public final int id;

  /**状态名*/
  public final String name;

  /**类型*/
  public final int type;

  /**类型功能是否触发多次*/
  public final int canReTrigger;

  /**进入状态后是否播其他动作*/
  public final int playAni;

  /**进入状态后是否播展示动作*/
  public final int playShowAni;

  /**跳过回合*/
  public final int skipTurn;

  /**是否封主动*/
  public final int banEnergySkill;

  /**是否封被动*/
  public final int banPassiveSkill;

  /**是否封S技*/
  public final int banSSkill;

  /**进入状态后是否触发技能*/
  public final int canTriggerSkill;

  /**进入状态后选择类型*/
  public final int selectType;

  /**驱散状态列表*/
  public final String dispelList;

  /**阻止状态列表*/
  public final String preventList;

  /**是否有状态动作*/
  public final int isPlayAnim;

  /**状态待机*/
  public final String stateAnim;

  /**状态死亡*/
  public final String stateDead;

  /**开始动作*/
  public final String stateStart;

  /**结束动作*/
  public final String stateFinish;

  /**开始特效*/
  public final String startEffect;

  /**持续特效*/
  public final String runEffect;

  /**结束特效*/
  public final String endEffect;

  /**状态优先级*/
  public final int statePriority;

  /**伤害命中加成*/
  public final int damageHitPro;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public AbnormalStateConfig(int id, String name, int type, int canReTrigger, int playAni, int playShowAni, int skipTurn, int banEnergySkill, int banPassiveSkill, int banSSkill, int canTriggerSkill, int selectType, String dispelList, String preventList, int isPlayAnim, String stateAnim, String stateDead, String stateStart, String stateFinish, String startEffect, String runEffect, String endEffect, int statePriority, int damageHitPro) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.canReTrigger = canReTrigger;
    this.playAni = playAni;
    this.playShowAni = playShowAni;
    this.skipTurn = skipTurn;
    this.banEnergySkill = banEnergySkill;
    this.banPassiveSkill = banPassiveSkill;
    this.banSSkill = banSSkill;
    this.canTriggerSkill = canTriggerSkill;
    this.selectType = selectType;
    this.dispelList = dispelList;
    this.preventList = preventList;
    this.isPlayAnim = isPlayAnim;
    this.stateAnim = stateAnim;
    this.stateDead = stateDead;
    this.stateStart = stateStart;
    this.stateFinish = stateFinish;
    this.startEffect = startEffect;
    this.runEffect = runEffect;
    this.endEffect = endEffect;
    this.statePriority = statePriority;
    this.damageHitPro = damageHitPro;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
