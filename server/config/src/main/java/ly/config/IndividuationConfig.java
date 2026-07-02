package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class IndividuationConfig {
  /**id*/
  public final int id;

  /**类型*/
  public final int type;

  /**道具id*/
  public final int item;

  /**参数1*/
  public final String param_1;

  /**场景id*/
  public final String sceneInfoId;

  /**图片资源*/
  public final String picRes;

  /**名称*/
  public final String name;

  /**备注*/
  public final String dec;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public IndividuationConfig(int id, int type, int item, String param_1, String sceneInfoId, String picRes, String name, String dec) {
    this.id = id;
    this.type = type;
    this.item = item;
    this.param_1 = param_1;
    this.sceneInfoId = sceneInfoId;
    this.picRes = picRes;
    this.name = name;
    this.dec = dec;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
