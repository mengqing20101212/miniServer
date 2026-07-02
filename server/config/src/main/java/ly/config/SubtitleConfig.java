package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SubtitleConfig {
  /**视频名称*/
  public final String name;

  /**对话序列*/
  public final int index;

  /**[样式,效果][1,1]*/
  public final String style;

  /**对话开始时间*/
  public final float start_time;

  /**对话结束时间*/
  public final float end_time;

  /**字幕内容*/
  public final String content;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SubtitleConfig(String name, int index, String style, float start_time, float end_time, String content) {
    this.name = name;
    this.index = index;
    this.style = style;
    this.start_time = start_time;
    this.end_time = end_time;
    this.content = content;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
