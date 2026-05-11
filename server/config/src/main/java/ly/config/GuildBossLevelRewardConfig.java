package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuildBossLevelRewardConfig {
  /**状态编号*/
  public final int id;

  /**BOSS等级*/
  public final int level;

  /**等级奖励*/
  public final int levelReward;

  /**前端等级奖励*/
  public final String disPlayLevelReward;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuildBossLevelRewardConfig(int id, int level, int levelReward, String disPlayLevelReward) {
    this.id = id;
    this.level = level;
    this.levelReward = levelReward;
    this.disPlayLevelReward = disPlayLevelReward;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
