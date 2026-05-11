package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class HeroAttrRankConfig {
  /**编号*/
  public final int id;

  /**备注*/
  public final String des;

  /**生命评级*/
  public final int maxHP;

  /**攻击评级*/
  public final int attack;

  /**防御评级*/
  public final int defence;

  /**速度评级*/
  public final int speed;

  /**暴击评级*/
  public final int crit;

  /**暴击伤害评级*/
  public final int critRatio;

  /**效果命中评级*/
  public final int effectHit;

  /**效果抵抗评级*/
  public final int effectDodge;

  /**回能评级*/
  public final int spCoe;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public HeroAttrRankConfig(int id, String des, int maxHP, int attack, int defence, int speed, int crit, int critRatio, int effectHit, int effectDodge, int spCoe) {
    this.id = id;
    this.des = des;
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
