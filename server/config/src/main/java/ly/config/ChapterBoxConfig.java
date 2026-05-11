package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ChapterBoxConfig {
  /**编号*/
  public final int id;

  /**章节ID*/
  public final int chapterMainId;

  /**奖励*/
  public final int drop;

  /**奖励预览*/
  public final int dropShow;

  /**类型*/
  public final int patterntype;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ChapterBoxConfig(int id, int chapterMainId, int drop, int dropShow, int patterntype) {
    this.id = id;
    this.chapterMainId = chapterMainId;
    this.drop = drop;
    this.dropShow = dropShow;
    this.patterntype = patterntype;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
