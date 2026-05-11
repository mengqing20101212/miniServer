package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DungeonRoomConfig {
  /**编号*/
  public final int id;

  /**所属Mainid*/
  public final int mainId;

  /**所属等级*/
  public final int levelId;

  /**启用线路*/
  public final int lineId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DungeonRoomConfig(int id, int mainId, int levelId, int lineId) {
    this.id = id;
    this.mainId = mainId;
    this.levelId = levelId;
    this.lineId = lineId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
