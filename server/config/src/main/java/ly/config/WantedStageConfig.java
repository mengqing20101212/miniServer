package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class WantedStageConfig {
  /**编号*/
  public final int id;

  /**任务标题*/
  public final String missionTitle;

  /**标题资源*/
  public final int titleRes;

  /**标题简报*/
  public final String titleDesc;

  /**任务介绍*/
  public final String missionDesc;

  /**任务图片*/
  public final int missionPic;

  /**星级*/
  public final int star;

  /**奖励展示*/
  public final String rewardShow;

  /**关卡id*/
  public final int sceneId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public WantedStageConfig(int id, String missionTitle, int titleRes, String titleDesc, String missionDesc, int missionPic, int star, String rewardShow, int sceneId) {
    this.id = id;
    this.missionTitle = missionTitle;
    this.titleRes = titleRes;
    this.titleDesc = titleDesc;
    this.missionDesc = missionDesc;
    this.missionPic = missionPic;
    this.star = star;
    this.rewardShow = rewardShow;
    this.sceneId = sceneId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
