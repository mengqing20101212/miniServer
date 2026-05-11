package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ElectronicThreeHeroConfig {
  /**编号*/
  public final int id;

  /**英雄id*/
  public final int heroId;

  /**英雄特性描述*/
  public final String heroDesc;

  /**权重*/
  public final int pro;

  /**组*/
  public final int group;

  /**1号位置源核属性*/
  public final String circuitAttr1;

  /**2号位置源核*/
  public final int circuit2;

  /**2号位置源核属性*/
  public final String circuitAttr2;

  /**3号位置源核*/
  public final int circuit3;

  /**3号位置源核属性*/
  public final String circuitAttr3;

  /**4号位置源核*/
  public final int circuit4;

  /**4号位置源核属性*/
  public final String circuitAttr4;

  /**5号位置源核*/
  public final int circuit5;

  /**5号位置源核属性*/
  public final String circuitAttr5;

  /**6号位置源核*/
  public final int circuit6;

  /**6号位置源核属性*/
  public final String circuitAttr6;

  /**7号位置源核*/
  public final int circuit7;

  /**7号位置源核属性*/
  public final String circuitAttr7;

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

  public ElectronicThreeHeroConfig(int id, int heroId, String heroDesc, int pro, int group, String circuitAttr1, int circuit2, String circuitAttr2, int circuit3, String circuitAttr3, int circuit4, String circuitAttr4, int circuit5, String circuitAttr5, int circuit6, String circuitAttr6, int circuit7, String circuitAttr7, int maxHP, int attack, int defence, int speed, int crit, int critRatio, int effectHit, int effectDodge, int spCoe) {
    this.id = id;
    this.heroId = heroId;
    this.heroDesc = heroDesc;
    this.pro = pro;
    this.group = group;
    this.circuitAttr1 = circuitAttr1;
    this.circuit2 = circuit2;
    this.circuitAttr2 = circuitAttr2;
    this.circuit3 = circuit3;
    this.circuitAttr3 = circuitAttr3;
    this.circuit4 = circuit4;
    this.circuitAttr4 = circuitAttr4;
    this.circuit5 = circuit5;
    this.circuitAttr5 = circuitAttr5;
    this.circuit6 = circuit6;
    this.circuitAttr6 = circuitAttr6;
    this.circuit7 = circuit7;
    this.circuitAttr7 = circuitAttr7;
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
