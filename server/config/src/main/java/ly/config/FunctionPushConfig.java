package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class FunctionPushConfig {
  /**编号*/
  public final int id;

  /**功能ID*/
  public final int functionId;

  /**排序*/
  public final int sort;

  /**图标*/
  public final int icon;

  /**跳转*/
  public final int turnId;

  /**提示*/
  public final int des;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public FunctionPushConfig(int id, int functionId, int sort, int icon, int turnId, int des) {
    this.id = id;
    this.functionId = functionId;
    this.sort = sort;
    this.icon = icon;
    this.turnId = turnId;
    this.des = des;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
