package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class StrongerConfig {
  /**编号*/
  public final int id;

  /**资源类型*/
  public final int resType;

  /**资源名称*/
  public final String resName;

  /**资源图标*/
  public final int resPic;

  /**资源描述*/
  public final String resDes;

  /**资源排序*/
  public final int resSort;

  /**玩法类型*/
  public final int playType;

  /**玩法名称（作废）*/
  public final String playName;

  /**推荐星级*/
  public final int playStar;

  /**跳转*/
  public final int playJump;

  /**玩法排序*/
  public final int playSort;

  /**道具类型*/
  public final int itemId;

  /**道具名称（作废）*/
  public final String itemName;

  /**道具排序*/
  public final int itemSort;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public StrongerConfig(int id, int resType, String resName, int resPic, String resDes, int resSort, int playType, String playName, int playStar, int playJump, int playSort, int itemId, String itemName, int itemSort) {
    this.id = id;
    this.resType = resType;
    this.resName = resName;
    this.resPic = resPic;
    this.resDes = resDes;
    this.resSort = resSort;
    this.playType = playType;
    this.playName = playName;
    this.playStar = playStar;
    this.playJump = playJump;
    this.playSort = playSort;
    this.itemId = itemId;
    this.itemName = itemName;
    this.itemSort = itemSort;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
