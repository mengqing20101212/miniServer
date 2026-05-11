package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class BravePointConfig {
  /**编号*/
  public final int id;

  /**类型*/
  public final int type;

  /**参数*/
  public final int para;

  /**分数变化*/
  public final int addCount;

  /**备注*/
  public final String beizhu;

  /**触发原因*/
  public final int triggerReason;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public BravePointConfig(int id, int type, int para, int addCount, String beizhu, int triggerReason) {
    this.id = id;
    this.type = type;
    this.para = para;
    this.addCount = addCount;
    this.beizhu = beizhu;
    this.triggerReason = triggerReason;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
