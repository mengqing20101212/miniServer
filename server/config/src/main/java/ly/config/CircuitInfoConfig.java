package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class CircuitInfoConfig {
  /**编号*/
  public final int id;

  /**回路显示种类*/
  public final int type;

  /**回路描述*/
  public final String description;

  /**回路主方向*/
  public final int mainForward;

  /**路线0*/
  public final String route0;

  /**路线1*/
  public final String route1;

  /**路线2*/
  public final String route2;

  /**路线3*/
  public final String route3;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public CircuitInfoConfig(int id, int type, String description, int mainForward, String route0, String route1, String route2, String route3) {
    this.id = id;
    this.type = type;
    this.description = description;
    this.mainForward = mainForward;
    this.route0 = route0;
    this.route1 = route1;
    this.route2 = route2;
    this.route3 = route3;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
