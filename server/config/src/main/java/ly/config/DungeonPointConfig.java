package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DungeonPointConfig {
  /**编号*/
  public final int id;

  /**备注*/
  public final String beizhu;

  /**类型*/
  public final int type;

  /**事件内容*/
  public final String para;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DungeonPointConfig(int id, String beizhu, int type, String para) {
    this.id = id;
    this.beizhu = beizhu;
    this.type = type;
    this.para = para;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
