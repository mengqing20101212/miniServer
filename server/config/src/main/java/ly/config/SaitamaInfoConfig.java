package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SaitamaInfoConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String title;

  /**图标*/
  public final int icon;

  /**展示标签*/
  public final String label;

  /**文字内容*/
  public final String word;

  /**预告文字内容*/
  public final String preWord;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SaitamaInfoConfig(int id, String title, int icon, String label, String word, String preWord) {
    this.id = id;
    this.title = title;
    this.icon = icon;
    this.label = label;
    this.word = word;
    this.preWord = preWord;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
