package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SceneChangeInfoConfig {
  /**编号*/
  public final int id;

  /**生命继承*/
  public final String hpInherit;

  /**气绝值继承*/
  public final String spInherit;

  /**鬼火继承*/
  public final String energyInherit;

  /**鬼火条继承*/
  public final String energyBarInherit;

  /**s能量继承*/
  public final String sPowerInherit;

  /**行动条继承*/
  public final String actionBarInherit;

  /**技能冷却继承*/
  public final String skillCDInherit;

  /**Bonus*/
  public final String bonusInherit;

  /**buff*/
  public final String buffInherit;

  /**全局论累计*/
  public final String globalRound;

  /**阵营轮累计*/
  public final String campRound;

  /**角色轮累计*/
  public final String charRound;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SceneChangeInfoConfig(int id, String hpInherit, String spInherit, String energyInherit, String energyBarInherit, String sPowerInherit, String actionBarInherit, String skillCDInherit, String bonusInherit, String buffInherit, String globalRound, String campRound, String charRound) {
    this.id = id;
    this.hpInherit = hpInherit;
    this.spInherit = spInherit;
    this.energyInherit = energyInherit;
    this.energyBarInherit = energyBarInherit;
    this.sPowerInherit = sPowerInherit;
    this.actionBarInherit = actionBarInherit;
    this.skillCDInherit = skillCDInherit;
    this.bonusInherit = bonusInherit;
    this.buffInherit = buffInherit;
    this.globalRound = globalRound;
    this.campRound = campRound;
    this.charRound = charRound;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
