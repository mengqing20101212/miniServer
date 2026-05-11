package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ChapterTaskConfig {
  /**编号*/
  public final int id;

  /**章节ID*/
  public final int chapterMainId;

  /**任务ID*/
  public final int missionId1;

  /**描述1*/
  public final String des1;

  /**跳转*/
  public final int redirectionId1;

  /**任务ID*/
  public final int missionId2;

  /**描述2*/
  public final String des2;

  /**跳转*/
  public final int redirectionId2;

  /**任务ID*/
  public final int missionId3;

  /**描述2*/
  public final String des3;

  /**跳转*/
  public final int redirectionId3;

  /**奖励*/
  public final int drop;

  /**奖励预览*/
  public final int dropShow;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ChapterTaskConfig(int id, int chapterMainId, int missionId1, String des1, int redirectionId1, int missionId2, String des2, int redirectionId2, int missionId3, String des3, int redirectionId3, int drop, int dropShow) {
    this.id = id;
    this.chapterMainId = chapterMainId;
    this.missionId1 = missionId1;
    this.des1 = des1;
    this.redirectionId1 = redirectionId1;
    this.missionId2 = missionId2;
    this.des2 = des2;
    this.redirectionId2 = redirectionId2;
    this.missionId3 = missionId3;
    this.des3 = des3;
    this.redirectionId3 = redirectionId3;
    this.drop = drop;
    this.dropShow = dropShow;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
