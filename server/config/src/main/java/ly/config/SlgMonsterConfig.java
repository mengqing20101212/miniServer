package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SlgMonsterConfig {
  /**战斗模板*/
  public final int id;

  /**最大生命*/
  public final long hp;

  /**攻击*/
  public final long attack;

  /**防御*/
  public final long defence;

  /**主动技能效果ID*/
  public final int activeSkill;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SlgMonsterConfig(int id, long hp, long attack, long defence, int activeSkill) {
    this.id = id;
    this.hp = hp;
    this.attack = attack;
    this.defence = defence;
    this.activeSkill = activeSkill;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}

  // @@@@@自定义方法结束区@@@@@
}
