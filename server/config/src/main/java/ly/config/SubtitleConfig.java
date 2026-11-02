package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SubtitleConfig {
  public final String name;

  public final int index;

  public final java.util.List<Integer> style;

  public final float start_time;

  public final float end_time;

  public final String content;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SubtitleConfig(String name, int index, java.util.List<Integer> style, float start_time, float end_time, String content) {
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
