package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ManualLevelConfig {
  /**编号*/
  public final int id;

  /**升级所需经验*/
  public final int nextLv;

  /**奖励展示*/
  public final String dropShow;

  /**实际掉落*/
  public final int drop;

  /**特殊奖励标记*/
  public final int sign;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ManualLevelConfig(int id, int nextLv, String dropShow, int drop, int sign) {
    this.id = id;
    this.nextLv = nextLv;
    this.dropShow = dropShow;
    this.drop = drop;
    this.sign = sign;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
