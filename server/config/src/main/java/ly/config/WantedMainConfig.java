package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class WantedMainConfig {
  /**编号*/
  public final int id;

  /**难度名称*/
  public final String rank;

  /**解锁所需数关数*/
  public final int rankUpNum;

  /**下一难度ID*/
  public final int nextId;

  /**上一难度ID*/
  public final int lastId;

  /**任务*/
  public final String mission;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public WantedMainConfig(int id, String rank, int rankUpNum, int nextId, int lastId, String mission) {
    this.id = id;
    this.rank = rank;
    this.rankUpNum = rankUpNum;
    this.nextId = nextId;
    this.lastId = lastId;
    this.mission = mission;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
