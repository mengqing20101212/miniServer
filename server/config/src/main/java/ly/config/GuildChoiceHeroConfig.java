package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuildChoiceHeroConfig {
  /**状态编号*/
  public final int id;

  /**解锁等级*/
  public final int unlcokLevel;

  /**英雄ID*/
  public final int npcid;

  /**玩家等级*/
  public final int playerLv;

  /**关卡ID*/
  public final int sceneId;

  /**优先级*/
  public final int priority;

  /**参与奖励*/
  public final int reward1;

  /**伤害奖励*/
  public final String reward2;

  /**boss技能*/
  public final String bossSkill;

  /**掉落显示*/
  public final int dropShow;

  /**BOSS伤害计算*/
  public final int bossDamageStatisticsType;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuildChoiceHeroConfig(int id, int unlcokLevel, int npcid, int playerLv, int sceneId, int priority, int reward1, String reward2, String bossSkill, int dropShow, int bossDamageStatisticsType) {
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
