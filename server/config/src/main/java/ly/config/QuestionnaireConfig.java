package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class QuestionnaireConfig {
  /**编号*/
  public final int id;

  /**开启等级*/
  public final int level;

  /**开启时间*/
  public final int stratTime;

  /**完成奖励邮件*/
  public final int mail;

  /**超链*/
  public final String connect;

  /**用于控制渠道显隐*/
  public final String channelId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public QuestionnaireConfig(int id, int level, int stratTime, int mail, String connect, String channelId) {
    this.id = id;
    this.level = level;
    this.stratTime = stratTime;
    this.mail = mail;
    this.connect = connect;
    this.channelId = channelId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
