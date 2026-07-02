package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class BGMConfig {
  /**编号*/
  public final int id;

  /**描述*/
  public final String description;

  /**场景对应的BGMID*/
  public final int BGMID;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public BGMConfig(int id, String description, int BGMID) {
    this.id = id;
    this.description = description;
    this.BGMID = BGMID;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
