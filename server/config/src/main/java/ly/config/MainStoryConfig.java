package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class MainStoryConfig {
  /**ID（任务唯一id）*/
  public final int id;

  /**任务名称*/
  public final String name;

  /**任务栏处名称*/
  public final String titile;

  /**任务栏目标*/
  public final String target;

  /**事件ID（多个用,隔开）*/
  public final String eventIds;

  /**章节名称图片资源*/
  public final int chapterRes;

  /**章节数字*/
  public final int chapterNum;

  /**通关全息调查*/
  public final int chapterId;

  /**解锁等级*/
  public final int requireLv;

  /**前续任务ID*/
  public final int preIds;

  /**后续任务ID*/
  public final int followID;

  /**掉落*/
  public final int dropGroupId;

  /**掉落展示*/
  public final String dropshow;

  /**未解锁提示*/
  public final String lockWord;

  /**图片*/
  public final int pic;

  /**关联showID*/
  public final int showId;

  /**图片2*/
  public final int pic2;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public MainStoryConfig(int id, String name, String titile, String target, String eventIds, int chapterRes, int chapterNum, int chapterId, int requireLv, int preIds, int followID, int dropGroupId, String dropshow, String lockWord, int pic, int showId, int pic2) {
    this.id = id;
    this.name = name;
    this.titile = titile;
    this.target = target;
    this.eventIds = eventIds;
    this.chapterRes = chapterRes;
    this.chapterNum = chapterNum;
    this.chapterId = chapterId;
    this.requireLv = requireLv;
    this.preIds = preIds;
    this.followID = followID;
    this.dropGroupId = dropGroupId;
    this.dropshow = dropshow;
    this.lockWord = lockWord;
    this.pic = pic;
    this.showId = showId;
    this.pic2 = pic2;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
