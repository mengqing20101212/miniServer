package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class CookMixConfig {
  /**编号*/
  public final int id;

  /**价值量下限*/
  public final int minArea;

  /**价值量上限*/
  public final int maxArea;

  /**掉落id*/
  public final int dropId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public CookMixConfig(int id, int minArea, int maxArea, int dropId) {
    this.id = id;
    this.minArea = minArea;
    this.maxArea = maxArea;
    this.dropId = dropId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
