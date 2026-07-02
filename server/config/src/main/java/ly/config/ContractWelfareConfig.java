package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ContractWelfareConfig {
  /**编号*/
  public final int id;

  /**保底次数*/
  public final int welfareNum;

  /**保底奖池*/
  public final int welfareDropId;

  /**保底奖池展示*/
  public final String welfareShow;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ContractWelfareConfig(int id, int welfareNum, int welfareDropId, String welfareShow) {
    this.id = id;
    this.welfareNum = welfareNum;
    this.welfareDropId = welfareDropId;
    this.welfareShow = welfareShow;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
