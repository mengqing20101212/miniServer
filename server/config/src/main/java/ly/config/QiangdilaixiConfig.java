package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class QiangdilaixiConfig {
  /**编号*/
  public final int id;

  /**备注名字*/
  public final String config_name;

  /**怪人名字*/
  public final String name;

  /**简介*/
  public final String detail;

  /**读取timeline名称*/
  public final String timeline;

  /**怪人UI资源*/
  public final int UIResource;

  /**怪人UI调整*/
  public final String ScalePosOffset;

  /**备注-时长*/
  public final String stopAt;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public QiangdilaixiConfig(int id, String config_name, String name, String detail, String timeline, int UIResource, String ScalePosOffset, String stopAt) {
    this.id = id;
    this.config_name = config_name;
    this.name = name;
    this.detail = detail;
    this.timeline = timeline;
    this.UIResource = UIResource;
    this.ScalePosOffset = ScalePosOffset;
    this.stopAt = stopAt;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
