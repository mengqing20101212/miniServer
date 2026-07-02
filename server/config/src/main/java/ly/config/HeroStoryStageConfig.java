package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class HeroStoryStageConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String stageName;

  /**组ID*/
  public final int groupId;

  /**下一关id*/
  public final int nextId;

  /**上一关id*/
  public final int lastId;

  /**实际关卡ID*/
  public final int sceneId;

  /**属性加成*/
  public final int statusBonus;

  /**奖励预览*/
  public final int dropShow;

  /**章节图片*/
  public final int storyBanner;

  /**章节名称*/
  public final String storyWord;

  /**关卡详情*/
  public final String mechanism;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public HeroStoryStageConfig(int id, String stageName, int groupId, int nextId, int lastId, int sceneId, int statusBonus, int dropShow, int storyBanner, String storyWord, String mechanism) {
    this.id = id;
    this.stageName = stageName;
    this.groupId = groupId;
    this.nextId = nextId;
    this.lastId = lastId;
    this.sceneId = sceneId;
    this.statusBonus = statusBonus;
    this.dropShow = dropShow;
    this.storyBanner = storyBanner;
    this.storyWord = storyWord;
    this.mechanism = mechanism;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
