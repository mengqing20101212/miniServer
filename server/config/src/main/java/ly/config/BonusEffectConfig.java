package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class BonusEffectConfig {
  /**编号*/
  public final int id;

  /**描述*/
  public final String description;

  /**图标*/
  public final int icon;

  /**效果*/
  public final int bonusEffect;

  /**是否为BuffId*/
  public final String isBonusBuff;

  /**效果类型*/
  public final String bonusType;

  /**被吃掉的效果*/
  public final String param_1;

  /**是否立刻消失*/
  public final String isVanish;

  /**分组*/
  public final int group;

  /**组内等级*/
  public final int level;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public BonusEffectConfig(int id, String description, int icon, int bonusEffect, String isBonusBuff, String bonusType, String param_1, String isVanish, int group, int level) {
    this.id = id;
    this.description = description;
    this.icon = icon;
    this.bonusEffect = bonusEffect;
    this.isBonusBuff = isBonusBuff;
    this.bonusType = bonusType;
    this.param_1 = param_1;
    this.isVanish = isVanish;
    this.group = group;
    this.level = level;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
