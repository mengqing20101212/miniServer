package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class HeroStoryMainConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String storyName;

  /**解锁等级*/
  public final int unlockLv;

  /**展示顺序*/
  public final int show;

  /**故事关卡*/
  public final String storyStage;

  /**是否显示英雄*/
  public final int isShow;

  /**关卡描述*/
  public final String stageDec;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public HeroStoryMainConfig(int id, String storyName, int unlockLv, int show, String storyStage, int isShow, String stageDec) {
    this.id = id;
    this.storyName = storyName;
    this.unlockLv = unlockLv;
    this.show = show;
    this.storyStage = storyStage;
    this.isShow = isShow;
    this.stageDec = stageDec;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
