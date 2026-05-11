package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityStoryConfig {
  /**编号*/
  public final int id;

  /**活动排期*/
  public final int scheDuling;

  /**活动说明*/
  public final String name;

  /**立绘*/
  public final String picture;

  /**全息标记*/
  public final int holographic;

  /**活动ID*/
  public final int activtyId;

  /**对应的活动id*/
  public final int trueActivityId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityStoryConfig(int id, int scheDuling, String name, String picture, int holographic, int activtyId, int trueActivityId) {
    this.id = id;
    this.scheDuling = scheDuling;
    this.name = name;
    this.picture = picture;
    this.holographic = holographic;
    this.activtyId = activtyId;
    this.trueActivityId = trueActivityId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
