package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class AcCommanderGiftConfig {
  /**ID*/
  public final int id;

  /**英雄参数*/
  public final String heroid;

  /**所属页签*/
  public final int page;

  /**任务名称*/
  public final String name;

  /**奖励展示*/
  public final int rewardShow;

  /**实际掉落*/
  public final int drop;

  /**跳转*/
  public final int redirectionId;

  /**图片配置*/
  public final String commanderShow;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public AcCommanderGiftConfig(int id, String heroid, int page, String name, int rewardShow, int drop, int redirectionId, String commanderShow) {
    this.id = id;
    this.heroid = heroid;
    this.page = page;
    this.name = name;
    this.rewardShow = rewardShow;
    this.drop = drop;
    this.redirectionId = redirectionId;
    this.commanderShow = commanderShow;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
