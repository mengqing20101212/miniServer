package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class StrategyRecommendLineupConfig {
  /**id*/
  public final int id;

  /**角色id（,）*/
  public final String heroIds;

  /**战斗关卡 id*/
  public final int sceneId;

  /**标题描述*/
  public final String title;

  /**出手顺序描述*/
  public final String actorDetail;

  /**玩法描述*/
  public final String playDetail;

  /**阵容效果描述*/
  public final String effectDetail;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public StrategyRecommendLineupConfig(int id, String heroIds, int sceneId, String title, String actorDetail, String playDetail, String effectDetail) {
    this.id = id;
    this.heroIds = heroIds;
    this.sceneId = sceneId;
    this.title = title;
    this.actorDetail = actorDetail;
    this.playDetail = playDetail;
    this.effectDetail = effectDetail;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
