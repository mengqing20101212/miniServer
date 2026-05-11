package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DreamEventNewConfig {
  /**编号*/
  public final int id;

  /**立绘id*/
  public final int resourceId;

  /**关卡机制*/
  public final String mechanism;

  /**可上阵英雄数量*/
  public final int heroCount;

  /**英雄限定*/
  public final String limit;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DreamEventNewConfig(int id, int resourceId, String mechanism, int heroCount, String limit) {
    this.id = id;
    this.resourceId = resourceId;
    this.mechanism = mechanism;
    this.heroCount = heroCount;
    this.limit = limit;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
