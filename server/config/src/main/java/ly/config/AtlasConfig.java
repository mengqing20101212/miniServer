package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class AtlasConfig {
  /**Spriteid*/
  public final int id;

  /**资源名*/
  public final String sprite;

  /**图集*/
  public final int atlas;

  /**描述*/
  public final String des;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public AtlasConfig(int id, String sprite, int atlas, String des) {
    this.id = id;
    this.sprite = sprite;
    this.atlas = atlas;
    this.des = des;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
