package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SocietyCleanCityConfig {
  /**编号*/
  public final int id;

  /**生成地格数量*/
  public final String cell;

  /**进度奖励25*/
  public final int progressdrop25;

  /**进度奖励50*/
  public final int progressdrop50;

  /**进度奖励75*/
  public final int progressdrop75;

  /**进度奖励100*/
  public final int progressdrop100;

  /**奖励事件*/
  public final String rewardEvent;

  /**宝箱事件*/
  public final String giveEvent;

  /**大奖*/
  public final String greatReward;

  /**维修事件*/
  public final String repairEvent;

  /**剧情事件*/
  public final String storyEvent;

  /**陷阱事件*/
  public final String trapEvent;

  /**BOSS事件*/
  public final String bossEvent;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SocietyCleanCityConfig(int id, String cell, int progressdrop25, int progressdrop50, int progressdrop75, int progressdrop100, String rewardEvent, String giveEvent, String greatReward, String repairEvent, String storyEvent, String trapEvent, String bossEvent) {
    this.id = id;
    this.cell = cell;
    this.progressdrop25 = progressdrop25;
    this.progressdrop50 = progressdrop50;
    this.progressdrop75 = progressdrop75;
    this.progressdrop100 = progressdrop100;
    this.rewardEvent = rewardEvent;
    this.giveEvent = giveEvent;
    this.greatReward = greatReward;
    this.repairEvent = repairEvent;
    this.storyEvent = storyEvent;
    this.trapEvent = trapEvent;
    this.bossEvent = bossEvent;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
