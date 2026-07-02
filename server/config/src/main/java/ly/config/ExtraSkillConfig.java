package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ExtraSkillConfig {
  /**id*/
  public final int id;

  /**掉落加成id*/
  public final int exDropId;

  /**备注*/
  public final String des;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ExtraSkillConfig(int id, int exDropId, String des) {
    this.id = id;
    this.exDropId = exDropId;
    this.des = des;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
