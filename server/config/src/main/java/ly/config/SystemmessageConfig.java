package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SystemmessageConfig {
  /**信息内容参数注释*/
  public final String comment;

  /**信息id*/
  public final int id;

  /**信息类型*/
  public final int type;

  /**物品类型*/
  public final int item_type;

  /**滚动速度*/
  public final int speed;

  /**时间*/
  public final int time;

  /**信息内容*/
  public final String message1;

  /**信息内容*/
  public final String message2;

  /**延迟播放时间*/
  public final int delayTime;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SystemmessageConfig(String comment, int id, int type, int item_type, int speed, int time, String message1, String message2, int delayTime) {
    this.comment = comment;
    this.id = id;
    this.type = type;
    this.item_type = item_type;
    this.speed = speed;
    this.time = time;
    this.message1 = message1;
    this.message2 = message2;
    this.delayTime = delayTime;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
