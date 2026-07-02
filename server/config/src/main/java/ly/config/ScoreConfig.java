package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ScoreConfig {
  /**账号等级*/
  public final int level;

  /**角色等级目标*/
  public final int heroLevelTarget;

  /**突破目标*/
  public final int breakTarget;

  /**进阶目标*/
  public final int advanceTarget;

  /**觉醒目标*/
  public final int awakenTarget;

  /**源核目标*/
  public final int suitTarget;

  /**表彰目标*/
  public final int supportTarget;

  /**角色等级系数*/
  public final float heroLevelCoefficient;

  /**突破系数*/
  public final float breakCoefficient;

  /**进阶系数*/
  public final float advanceCoefficient;

  /**觉醒系数*/
  public final float awakenCoefficient;

  /**源核系数*/
  public final float suitCoefficient;

  /**表彰系数*/
  public final float supportCoefficient;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ScoreConfig(int level, int heroLevelTarget, int breakTarget, int advanceTarget, int awakenTarget, int suitTarget, int supportTarget, float heroLevelCoefficient, float breakCoefficient, float advanceCoefficient, float awakenCoefficient, float suitCoefficient, float supportCoefficient) {
    this.level = level;
    this.heroLevelTarget = heroLevelTarget;
    this.breakTarget = breakTarget;
    this.advanceTarget = advanceTarget;
    this.awakenTarget = awakenTarget;
    this.suitTarget = suitTarget;
    this.supportTarget = supportTarget;
    this.heroLevelCoefficient = heroLevelCoefficient;
    this.breakCoefficient = breakCoefficient;
    this.advanceCoefficient = advanceCoefficient;
    this.awakenCoefficient = awakenCoefficient;
    this.suitCoefficient = suitCoefficient;
    this.supportCoefficient = supportCoefficient;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
