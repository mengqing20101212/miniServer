package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class TrialawardConfig {
  /**策划用介绍*/
  public final String desc;

  /**试炼类型ID*/
  public final int type;

  /**条件*/
  public final int condition;

  /**达成条件描述*/
  public final int conditiondesc;

  /**掉落集id*/
  public final int award;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public TrialawardConfig(String desc, int type, int condition, int conditiondesc, int award) {
    this.desc = desc;
    this.type = type;
    this.condition = condition;
    this.conditiondesc = conditiondesc;
    this.award = award;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
