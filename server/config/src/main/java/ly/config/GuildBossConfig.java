package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuildBossConfig {
  public final int id;

  public final int sceneId;

  public final int level;

  public final int group;

  public final int icon;

  public final String battleReward;

  public final int killReward;

  public final String disPlayBattleReward;

  public final String disPlayKillReward;

  public final String bossDesc;

  public final String battleDesc;

  public final String name;

  public final int bossId;

  public final java.util.List<Integer> bossSkill;

  public final int bossDamageStatisticsType;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuildBossConfig(int id, int sceneId, int level, int group, int icon, String battleReward, int killReward, String disPlayBattleReward, String disPlayKillReward, String bossDesc, String battleDesc, String name, int bossId, java.util.List<Integer> bossSkill, int bossDamageStatisticsType) {
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
