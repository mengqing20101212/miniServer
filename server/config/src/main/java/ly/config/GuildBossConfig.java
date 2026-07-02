package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuildBossConfig {
  /**状态编号*/
  public final int id;

  /**对应关卡*/
  public final int sceneId;

  /**BOSS等级*/
  public final int level;

  /**BOSS组ID*/
  public final int group;

  /**boss半身像*/
  public final int icon;

  /**战斗奖励*/
  public final String battleReward;

  /**击败奖励*/
  public final int killReward;

  /**前端展示战斗奖励*/
  public final String disPlayBattleReward;

  /**前端展示击败奖励*/
  public final String disPlayKillReward;

  /**BOSS特性*/
  public final String bossDesc;

  /**战斗限制*/
  public final String battleDesc;

  /**boss名字*/
  public final String name;

  /**bossId*/
  public final int bossId;

  /**boss技能*/
  public final String bossSkill;

  /**BOSS伤害计算*/
  public final int bossDamageStatisticsType;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuildBossConfig(int id, int sceneId, int level, int group, int icon, String battleReward, int killReward, String disPlayBattleReward, String disPlayKillReward, String bossDesc, String battleDesc, String name, int bossId, String bossSkill, int bossDamageStatisticsType) {
    this.id = id;
    this.sceneId = sceneId;
    this.level = level;
    this.group = group;
    this.icon = icon;
    this.battleReward = battleReward;
    this.killReward = killReward;
    this.disPlayBattleReward = disPlayBattleReward;
    this.disPlayKillReward = disPlayKillReward;
    this.bossDesc = bossDesc;
    this.battleDesc = battleDesc;
    this.name = name;
    this.bossId = bossId;
    this.bossSkill = bossSkill;
    this.bossDamageStatisticsType = bossDamageStatisticsType;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
