package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SignLoopConfig {
  /**编号*/
  public final int id;

  /**组*/
  public final int guoup;

  /**奖励*/
  public final int drop;

  /**奖励显示*/
  public final int dropShow;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SignLoopConfig(int id, int guoup, int drop, int dropShow) {
    this.id = id;
    this.guoup = guoup;
    this.drop = drop;
    this.dropShow = dropShow;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
