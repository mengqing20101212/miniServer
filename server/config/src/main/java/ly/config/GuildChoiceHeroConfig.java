package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuildChoiceHeroConfig {
  public final int id;

  public final int unlcokLevel;

  public final int npcid;

  public final int playerLv;

  public final int sceneId;

  public final int priority;

  public final int reward1;

  public final String reward2;

  public final java.util.List<Integer> bossSkill;

  public final int dropShow;

  public final int bossDamageStatisticsType;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuildChoiceHeroConfig(int id, int unlcokLevel, int npcid, int playerLv, int sceneId, int priority, int reward1, String reward2, java.util.List<Integer> bossSkill, int dropShow, int bossDamageStatisticsType) {
    this.id = id;
    this.unlcokLevel = unlcokLevel;
    this.npcid = npcid;
    this.playerLv = playerLv;
    this.sceneId = sceneId;
    this.priority = priority;
    this.reward1 = reward1;
    this.reward2 = reward2;
    this.bossSkill = bossSkill;
    this.dropShow = dropShow;
    this.bossDamageStatisticsType = bossDamageStatisticsType;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
