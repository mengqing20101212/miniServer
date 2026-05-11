package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class MissionMain2Config {
  /**编号*/
  public final int id;

  /**备注*/
  public final String beizhu;

  /**内容类型*/
  public final int condition;

  /**参数*/
  public final int para;

  /**参数2*/
  public final int para2;

  /**目标值*/
  public final int targetValue;

  /**数据统计方式*/
  public final int change_methods;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public MissionMain2Config(int id, String beizhu, int condition, int para, int para2, int targetValue, int change_methods) {
    this.id = id;
    this.beizhu = beizhu;
    this.condition = condition;
    this.para = para;
    this.para2 = para2;
    this.targetValue = targetValue;
    this.change_methods = change_methods;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
