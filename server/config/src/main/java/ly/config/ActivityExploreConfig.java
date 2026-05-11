package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityExploreConfig {
  /**序号*/
  public final int id;

  /**期数*/
  public final int scheDuling;

  /**层数*/
  public final int layers;

  /**特殊奖励*/
  public final String specialReward;

  /**特殊奖励展示*/
  public final int dropShowId;

  /**普通奖励*/
  public final String normalReward;

  /**地图关卡*/
  public final String exploreMap;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityExploreConfig(int id, int scheDuling, int layers, String specialReward, int dropShowId, String normalReward, String exploreMap) {
    this.id = id;
    this.scheDuling = scheDuling;
    this.layers = layers;
    this.specialReward = specialReward;
    this.dropShowId = dropShowId;
    this.normalReward = normalReward;
    this.exploreMap = exploreMap;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
