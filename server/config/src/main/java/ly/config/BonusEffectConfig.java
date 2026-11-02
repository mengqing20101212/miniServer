package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class BonusEffectConfig {
  public final int id;

  public final String description;

  public final int icon;

  public final int bonusEffect;

  public final boolean isBonusBuff;

  public final String bonusType;

  public final String param_1;

  public final String isVanish;

  public final int group;

  public final int level;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public BonusEffectConfig(int id, String description, int icon, int bonusEffect, boolean isBonusBuff, String bonusType, String param_1, String isVanish, int group, int level) {
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
