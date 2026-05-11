package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ReserverecruitConfig {
  /**编号*/
  public final int id;

  /**品质保底类型*/
  public final int welfareId;

  /**招募次数*/
  public final int recruitNum;

  /**掉落列表*/
  public final String awardList;

  /**掉落概率*/
  public final String awardRelativePro;

  /**注释*/
  public final String desc;

  /**每日上限*/
  public final int dayLimit;

  /**100次保底SSR数量*/
  public final int Guaranteed;

  /**保底SSR范围*/
  public final String Guaranteerange;

  /**活动开启间隔时间*/
  public final int intervaltime;

  /**领奖次数*/
  public final int Receiveaward;

  /**每次10连SSR上限*/
  public final int ssrupperlimit;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ReserverecruitConfig(int id, int welfareId, int recruitNum, String awardList, String awardRelativePro, String desc, int dayLimit, int Guaranteed, String Guaranteerange, int intervaltime, int Receiveaward, int ssrupperlimit) {
    this.id = id;
    this.welfareId = welfareId;
    this.recruitNum = recruitNum;
    this.awardList = awardList;
    this.awardRelativePro = awardRelativePro;
    this.desc = desc;
    this.dayLimit = dayLimit;
    this.Guaranteed = Guaranteed;
    this.Guaranteerange = Guaranteerange;
    this.intervaltime = intervaltime;
    this.Receiveaward = Receiveaward;
    this.ssrupperlimit = ssrupperlimit;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
