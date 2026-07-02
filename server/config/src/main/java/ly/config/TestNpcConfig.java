package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class TestNpcConfig {
  /**编号*/
  public final int id;

  /**名字*/
  public final String name;

  /**等级*/
  public final int level;

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

  /**常规技能组*/
  public final String skillList;

  /**S技能*/
  public final String Sskill;

  /**AI模板*/
  public final String aiName;

  /**模型id*/
  public final int modelId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public TestNpcConfig(int id, String name, int level, int maxHP, int attack, int defence, int speed, int crit, int critRatio, int effectHit, int effectDodge, String skillList, String Sskill, String aiName, int modelId) {
    this.id = id;
    this.name = name;
    this.level = level;
    this.maxHP = maxHP;
    this.attack = attack;
    this.defence = defence;
    this.speed = speed;
    this.crit = crit;
    this.critRatio = critRatio;
    this.effectHit = effectHit;
    this.effectDodge = effectDodge;
    this.skillList = skillList;
    this.Sskill = Sskill;
    this.aiName = aiName;
    this.modelId = modelId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
