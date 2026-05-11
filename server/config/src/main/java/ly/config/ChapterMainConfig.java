package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ChapterMainConfig {
  /**编号*/
  public final int id;

  /**类型*/
  public final int patterntype;

  /**名称*/
  public final String storyName;

  /**章节展示*/
  public final String chapterShow;

  /**解锁等级*/
  public final int unlockLv;

  /**解锁主线*/
  public final int storyId;

  /**下一章节*/
  public final int nextChapter;

  /**上一章节*/
  public final int lastChapter;

  /**关卡*/
  public final String chapterStage;

  /**星级宝箱*/
  public final String starReward;

  /**奖励预览*/
  public final String rewardShow;

  /**未解锁提示*/
  public final String unlockWord;

  /**背景资源*/
  public final int background;

  /**章节介绍*/
  public final String introduce;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ChapterMainConfig(int id, int patterntype, String storyName, String chapterShow, int unlockLv, int storyId, int nextChapter, int lastChapter, String chapterStage, String starReward, String rewardShow, String unlockWord, int background, String introduce) {
    this.id = id;
    this.patterntype = patterntype;
    this.storyName = storyName;
    this.chapterShow = chapterShow;
    this.unlockLv = unlockLv;
    this.storyId = storyId;
    this.nextChapter = nextChapter;
    this.lastChapter = lastChapter;
    this.chapterStage = chapterStage;
    this.starReward = starReward;
    this.rewardShow = rewardShow;
    this.unlockWord = unlockWord;
    this.background = background;
    this.introduce = introduce;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
