package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PraiseRewardConfig {
  /**编号*/
  public final int id;

  /**类型*/
  public final int type;

  /**前端显示*/
  public final String rewardShow;

  /**后端掉落*/
  public final int drop;

  /**进度*/
  public final int value;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PraiseRewardConfig(int id, int type, String rewardShow, int drop, int value) {
    this.id = id;
    this.type = type;
    this.rewardShow = rewardShow;
    this.drop = drop;
    this.value = value;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
