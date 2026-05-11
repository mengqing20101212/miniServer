package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ExchangeCodeConfig {
  /**自增id*/
  public final int groupId;

  /**礼包名称*/
  public final String name;

  /**开始领取时间*/
  public final String beginTime;

  /**结束领取时间*/
  public final String endTime;

  /**生成兑换码个数*/
  public final int codeNum;

  /**渠道id*/
  public final String channel;

  /**获取的奖励列表*/
  public final String rewards;

  /**一个礼包码可有多少个角色激活，-1表示不限*/
  public final int limit1;

  /**一个角色可以激活同一批礼包码数量*/
  public final int limit2;

  /**该批礼包码能够被那些服务器使用，-1为所有*/
  public final String limit3;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ExchangeCodeConfig(int groupId, String name, String beginTime, String endTime, int codeNum, String channel, String rewards, int limit1, int limit2, String limit3) {
    this.groupId = groupId;
    this.name = name;
    this.beginTime = beginTime;
    this.endTime = endTime;
    this.codeNum = codeNum;
    this.channel = channel;
    this.rewards = rewards;
    this.limit1 = limit1;
    this.limit2 = limit2;
    this.limit3 = limit3;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
