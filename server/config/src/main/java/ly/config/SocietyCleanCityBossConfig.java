package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SocietyCleanCityBossConfig {
  /**状态编号*/
  public final int id;

  /**对应关卡*/
  public final int sceneId;

  /**战斗奖励*/
  public final String battleReward;

  /**击败奖励*/
  public final int killReward;

  /**前端展示战斗奖励*/
  public final String disPlayBattleReward;

  /**前端展示击败奖励*/
  public final String disPlayKillReward;

  /**bossId*/
  public final int bossId;

  /**BOSS伤害计算*/
  public final int bossDamageStatisticsType;

  /**spine显示模型预设资源Id*/
  public final int spineModelResId;

  /**spine动画*/
  public final String spineAnimation;

  /**缩放*/
  public final int scale;

  /**坐标*/
  public final String coordinate;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SocietyCleanCityBossConfig(int id, int sceneId, String battleReward, int killReward, String disPlayBattleReward, String disPlayKillReward, int bossId, int bossDamageStatisticsType, int spineModelResId, String spineAnimation, int scale, String coordinate) {
    this.id = id;
    this.sceneId = sceneId;
    this.battleReward = battleReward;
    this.killReward = killReward;
    this.disPlayBattleReward = disPlayBattleReward;
    this.disPlayKillReward = disPlayKillReward;
    this.bossId = bossId;
    this.bossDamageStatisticsType = bossDamageStatisticsType;
    this.spineModelResId = spineModelResId;
    this.spineAnimation = spineAnimation;
    this.scale = scale;
    this.coordinate = coordinate;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
