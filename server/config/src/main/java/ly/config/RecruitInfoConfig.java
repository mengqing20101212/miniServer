package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class RecruitInfoConfig {
  /**编号*/
  public final int id;

  /**品质保底类型*/
  public final int welfareId;

  /**招募类型*/
  public final int recruitType;

  /**活动排期*/
  public final int scheDuling;

  /**对应的活动id*/
  public final int trueActivityId;

  /**招募道具*/
  public final int item;

  /**跳转ID*/
  public final int turnId;

  /**消耗数量*/
  public final int num;

  /**招募次数*/
  public final int recruitNum;

  /**对应掉落组*/
  public final int awardId;

  /**累计次数掉落组*/
  public final String sumAwardId;

  /**注释*/
  public final String desc;

  /**每日上限*/
  public final int dayLimit;

  /**抽卡上限ID*/
  public final int dayLimitId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public RecruitInfoConfig(int id, int welfareId, int recruitType, int scheDuling, int trueActivityId, int item, int turnId, int num, int recruitNum, int awardId, String sumAwardId, String desc, int dayLimit, int dayLimitId) {
    this.id = id;
    this.welfareId = welfareId;
    this.recruitType = recruitType;
    this.scheDuling = scheDuling;
    this.trueActivityId = trueActivityId;
    this.item = item;
    this.turnId = turnId;
    this.num = num;
    this.recruitNum = recruitNum;
    this.awardId = awardId;
    this.sumAwardId = sumAwardId;
    this.desc = desc;
    this.dayLimit = dayLimit;
    this.dayLimitId = dayLimitId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
