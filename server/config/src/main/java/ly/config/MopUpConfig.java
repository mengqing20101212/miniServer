package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class MopUpConfig {
  /**编号*/
  public final int id;

  /**道具id*/
  public final int itemId;

  /**关卡ID*/
  public final int chapterStage;

  /**期望掉落数量*/
  public final int dropExpect;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public MopUpConfig(int id, int itemId, int chapterStage, int dropExpect) {
    this.id = id;
    this.itemId = itemId;
    this.chapterStage = chapterStage;
    this.dropExpect = dropExpect;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
