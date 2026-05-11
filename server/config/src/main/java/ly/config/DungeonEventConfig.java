package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DungeonEventConfig {
  /**编号*/
  public final int id;

  /**组编号*/
  public final int groupId;

  /**组内难度顺序*/
  public final int groupNum;

  /**类型*/
  public final int type;

  /**事件名称*/
  public final String eventName;

  /**事件名称资源Id(目前不用)*/
  public final int eventNameRes;

  /**描述*/
  public final String dis;

  /**事件点底图资源id*/
  public final int baseRes;

  /**事件标题资源id*/
  public final int titleRes;

  /**事件内容资源id*/
  public final String contentRes;

  /**参数1*/
  public final String para1;

  /**参数2*/
  public final String para2;

  /**参数3*/
  public final int para3;

  /**显示内容*/
  public final String showContent;

  /**显示类型*/
  public final int showType;

  /**事件点底图资源灰度id*/
  public final int baseResGray;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DungeonEventConfig(int id, int groupId, int groupNum, int type, String eventName, int eventNameRes, String dis, int baseRes, int titleRes, String contentRes, String para1, String para2, int para3, String showContent, int showType, int baseResGray) {
    this.id = id;
    this.groupId = groupId;
    this.groupNum = groupNum;
    this.type = type;
    this.eventName = eventName;
    this.eventNameRes = eventNameRes;
    this.dis = dis;
    this.baseRes = baseRes;
    this.titleRes = titleRes;
    this.contentRes = contentRes;
    this.para1 = para1;
    this.para2 = para2;
    this.para3 = para3;
    this.showContent = showContent;
    this.showType = showType;
    this.baseResGray = baseResGray;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
