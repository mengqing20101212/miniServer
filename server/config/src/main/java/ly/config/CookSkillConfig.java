package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class CookSkillConfig {
  /**等级*/
  public final int skill;

  /**技巧组*/
  public final int group;

  /**下一级所需经验*/
  public final int nextExp;

  /**加成系数（%）*/
  public final int bonus;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public CookSkillConfig(int skill, int group, int nextExp, int bonus) {
    this.skill = skill;
    this.group = group;
    this.nextExp = nextExp;
    this.bonus = bonus;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
