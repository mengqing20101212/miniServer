package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ElectronicFairHeroConfig {
  /**编号*/
  public final int id;

  /**英雄id*/
  public final int heroId;

  /**等级*/
  public final int Level;

  /**星级*/
  public final int star;

  /**觉醒等级*/
  public final int awaken;

  /**技能1等级*/
  public final int skill1;

  /**技能2等级*/
  public final int skill2;

  /**技能3等级*/
  public final int skill3;

  /**技能S等级*/
  public final int skillS;

  /**生命*/
  public final int maxHP;

  /**攻击*/
  public final int attack;

  /**防御*/
  public final int defence;

  /**速度*/
  public final int speed;

  /**暴击*/
  public final int crit;

  /**暴伤*/
  public final int critRatio;

  /**命中*/
  public final int effectHit;

  /**抵抗*/
  public final int effectDodge;

  /**回能*/
  public final int spCoe;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ElectronicFairHeroConfig(int id, int heroId, int Level, int star, int awaken, int skill1, int skill2, int skill3, int skillS, int maxHP, int attack, int defence, int speed, int crit, int critRatio, int effectHit, int effectDodge, int spCoe) {
    this.id = id;
    this.heroId = heroId;
    this.Level = Level;
    this.star = star;
    this.awaken = awaken;
    this.skill1 = skill1;
    this.skill2 = skill2;
    this.skill3 = skill3;
    this.skillS = skillS;
    this.maxHP = maxHP;
    this.attack = attack;
    this.defence = defence;
    this.speed = speed;
    this.crit = crit;
    this.critRatio = critRatio;
    this.effectHit = effectHit;
    this.effectDodge = effectDodge;
    this.spCoe = spCoe;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
