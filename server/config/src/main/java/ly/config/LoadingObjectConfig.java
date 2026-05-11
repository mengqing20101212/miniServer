package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class LoadingObjectConfig {
  /**id*/
  public final int id;

  /**描述*/
  public final String describe;

  /**类型*/
  public final int type;

  /**参数(待定 目前不用)*/
  public final String para;

  /**视频Ids（依序播放）*/
  public final String videoId;

  /**图片Id*/
  public final int pictureId;

  /**背景颜色*/
  public final String bgColor;

  /**声音Id*/
  public final int audioId;

  /**是否有白屏过渡*/
  public final int isHaveTransition;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public LoadingObjectConfig(int id, String describe, int type, String para, String videoId, int pictureId, String bgColor, int audioId, int isHaveTransition) {
    this.id = id;
    this.describe = describe;
    this.type = type;
    this.para = para;
    this.videoId = videoId;
    this.pictureId = pictureId;
    this.bgColor = bgColor;
    this.audioId = audioId;
    this.isHaveTransition = isHaveTransition;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
