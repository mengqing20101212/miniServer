package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class StrongRoadConfig {
  /**id*/
  public final int id;

  /**类型*/
  public final int type;

  /**参数*/
  public final String para;

  /**主界面显示*/
  public final int iconShow;

  /**是否显示*/
  public final int show;

  /**名称*/
  public final String name;

  /**图标*/
  public final int icon;

  /**跳转id*/
  public final int turnId;

  /**描述*/
  public final String des;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public StrongRoadConfig(int id, int type, String para, int iconShow, int show, String name, int icon, int turnId, String des) {
    this.id = id;
    this.type = type;
    this.para = para;
    this.iconShow = iconShow;
    this.show = show;
    this.name = name;
    this.icon = icon;
    this.turnId = turnId;
    this.des = des;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
