package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DungeonLeaderConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String name;

  /**参数*/
  public final int para;

  /**类型id*/
  public final int type;

  /**领队效果文字说明*/
  public final String leaderEffectContent;

  /**领队全身像*/
  public final int bodyPic;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DungeonLeaderConfig(int id, String name, int para, int type, String leaderEffectContent, int bodyPic) {
    this.id = id;
    this.name = name;
    this.para = para;
    this.type = type;
    this.leaderEffectContent = leaderEffectContent;
    this.bodyPic = bodyPic;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
