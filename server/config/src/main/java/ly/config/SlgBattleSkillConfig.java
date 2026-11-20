package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SlgBattleSkillConfig {
  /**SLG效果ID*/
  public final int id;

  /**关联英雄超级技能ID（0为公共技能）*/
  public final int heroSkillId;

  /**战斗效果*/
  public final int effectType;

  /**目标规则*/
  public final int targetRule;

  /**目标数*/
  public final int targetCount;

  /**技能千分比*/
  public final int actionRate;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SlgBattleSkillConfig(int id, int heroSkillId, int effectType, int targetRule, int targetCount, int actionRate) {
    this.id = id;
    this.heroSkillId = heroSkillId;
    this.effectType = effectType;
    this.targetRule = targetRule;
    this.targetCount = targetCount;
    this.actionRate = actionRate;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}

  // @@@@@自定义方法结束区@@@@@
}
