package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class BlockBattleHomeConfig {
  /**编号*/
  public final int id;

  /**主场名称*/
  public final String name;

  /**关卡ID*/
  public final int sceneInfo;

  /**缩略图*/
  public final int image;

  /**主场效果描述*/
  public final String Dec;

  /**解锁任务id*/
  public final int missionId;

  /**解锁描述*/
  public final String unlockDec;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public BlockBattleHomeConfig(int id, String name, int sceneInfo, int image, String Dec, int missionId, String unlockDec) {
    this.id = id;
    this.name = name;
    this.sceneInfo = sceneInfo;
    this.image = image;
    this.Dec = Dec;
    this.missionId = missionId;
    this.unlockDec = unlockDec;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
