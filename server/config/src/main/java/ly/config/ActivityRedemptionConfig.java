package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityRedemptionConfig {
  /**编号*/
  public final int id;

  /**任务名称*/
  public final String name;

  /**任务类型*/
  public final int questType;

  /**活动排期*/
  public final int scheDuling;

  /**兑换参数*/
  public final String exchangeNum;

  /**兑换奖励*/
  public final int drop;

  /**奖励展示*/
  public final int dropShow;

  /**可兑换次数*/
  public final int finishMax;

  /**每日重置*/
  public final int reset;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityRedemptionConfig(int id, String name, int questType, int scheDuling, String exchangeNum, int drop, int dropShow, int finishMax, int reset) {
    this.id = id;
    this.name = name;
    this.questType = questType;
    this.scheDuling = scheDuling;
    this.exchangeNum = exchangeNum;
    this.drop = drop;
    this.dropShow = dropShow;
    this.finishMax = finishMax;
    this.reset = reset;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
