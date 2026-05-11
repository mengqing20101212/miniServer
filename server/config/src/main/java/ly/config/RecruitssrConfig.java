package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class RecruitssrConfig {
  /**索引ID*/
  public final int id;

  /**抽卡数量区间*/
  public final int section;

  /**UP池dropID*/
  public final int awardpool;

  /**活动排期*/
  public final int scheDuling;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public RecruitssrConfig(int id, int section, int awardpool, int scheDuling) {
    this.id = id;
    this.section = section;
    this.awardpool = awardpool;
    this.scheDuling = scheDuling;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
