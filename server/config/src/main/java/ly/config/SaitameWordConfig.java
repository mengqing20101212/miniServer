package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SaitameWordConfig {
  /**编号*/
  public final int id;

  /**对话组*/
  public final int group;

  /**说话人*/
  public final int talker;

  /**文字内容*/
  public final String word;

  /**坐标*/
  public final String coordinate;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SaitameWordConfig(int id, int group, int talker, String word, String coordinate) {
    this.id = id;
    this.group = group;
    this.talker = talker;
    this.word = word;
    this.coordinate = coordinate;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
