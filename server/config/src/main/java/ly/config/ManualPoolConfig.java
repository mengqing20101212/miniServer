package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ManualPoolConfig {
  /**编号*/
  public final int id;

  /**等级档位*/
  public final String level;

  /**固定任务池每日*/
  public final String regularPoolDaily;

  /**随机池随机数量每日*/
  public final String randomNumDaily;

  /**随机池每日*/
  public final String poolGroupDaily;

  /**固定任务池每周*/
  public final String regularPoolWeek;

  /**随机池随机数量每周*/
  public final String randomNumWeek;

  /**随机池每周*/
  public final String poolGroupWeek;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ManualPoolConfig(int id, String level, String regularPoolDaily, String randomNumDaily, String poolGroupDaily, String regularPoolWeek, String randomNumWeek, String poolGroupWeek) {
    this.id = id;
    this.level = level;
    this.regularPoolDaily = regularPoolDaily;
    this.randomNumDaily = randomNumDaily;
    this.poolGroupDaily = poolGroupDaily;
    this.regularPoolWeek = regularPoolWeek;
    this.randomNumWeek = randomNumWeek;
    this.poolGroupWeek = poolGroupWeek;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
