package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SceneChangeInfoConfig {
  public final int id;

  public final boolean hpInherit;

  public final boolean spInherit;

  public final boolean energyInherit;

  public final boolean energyBarInherit;

  public final boolean sPowerInherit;

  public final boolean actionBarInherit;

  public final boolean skillCDInherit;

  public final boolean bonusInherit;

  public final boolean buffInherit;

  public final boolean globalRound;

  public final boolean campRound;

  public final boolean charRound;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SceneChangeInfoConfig(int id, boolean hpInherit, boolean spInherit, boolean energyInherit, boolean energyBarInherit, boolean sPowerInherit, boolean actionBarInherit, boolean skillCDInherit, boolean bonusInherit, boolean buffInherit, boolean globalRound, boolean campRound, boolean charRound) {
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
