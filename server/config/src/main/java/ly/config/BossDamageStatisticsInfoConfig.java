package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class BossDamageStatisticsInfoConfig {
  /**编号*/
  public final int id;

  /**计算类型*/
  public final int type;

  /**参数1*/
  public final String parameter1;

  /**参数2*/
  public final String parameter2;

  /**参数3*/
  public final String parameter3;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public BossDamageStatisticsInfoConfig(int id, int type, String parameter1, String parameter2, String parameter3) {
    this.id = id;
    this.type = type;
    this.parameter1 = parameter1;
    this.parameter2 = parameter2;
    this.parameter3 = parameter3;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
