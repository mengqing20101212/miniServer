package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivitywishingConfig {
  /**编号*/
  public final int id;

  /**许愿次数次数*/
  public final int wishingNum;

  /**掉落列表*/
  public final String awardList;

  /**掉落权重*/
  public final String awardRelativePro;

  /**注释*/
  public final String desc;

  /**每日上限*/
  public final int dayLimit;

  /**10次保底道具数量*/
  public final int Guaranteed;

  /**保底道具范围*/
  public final String Guaranteerange;

  /**保底道具权重*/
  public final String GuaranteerangeWeights;

  /**活动开启间隔时间*/
  public final int intervaltime;

  /**领奖次数*/
  public final int Receiveaward;

  /**奖励展示*/
  public final String rewardShow;

  /**活动图片1*/
  public final String pictures1;

  /**活动图片2*/
  public final String pictures2;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivitywishingConfig(int id, int wishingNum, String awardList, String awardRelativePro, String desc, int dayLimit, int Guaranteed, String Guaranteerange, String GuaranteerangeWeights, int intervaltime, int Receiveaward, String rewardShow, String pictures1, String pictures2) {
    this.id = id;
    this.wishingNum = wishingNum;
    this.awardList = awardList;
    this.awardRelativePro = awardRelativePro;
    this.desc = desc;
    this.dayLimit = dayLimit;
    this.Guaranteed = Guaranteed;
    this.Guaranteerange = Guaranteerange;
    this.GuaranteerangeWeights = GuaranteerangeWeights;
    this.intervaltime = intervaltime;
    this.Receiveaward = Receiveaward;
    this.rewardShow = rewardShow;
    this.pictures1 = pictures1;
    this.pictures2 = pictures2;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
