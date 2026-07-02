package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class MinigameMainConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String name;

  /**资源图*/
  public final int banner;

  /**需要货币*/
  public final String ticket;

  /**玩法规则说明*/
  public final String rule;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public MinigameMainConfig(int id, String name, int banner, String ticket, String rule) {
    this.id = id;
    this.name = name;
    this.banner = banner;
    this.ticket = ticket;
    this.rule = rule;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
