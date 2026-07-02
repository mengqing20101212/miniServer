package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SaitamaActionConfig {
  /**编号*/
  public final int id;

  /**编号*/
  public final int people;

  /**动作*/
  public final String action;

  /**备注*/
  public final String beizhu;

  /**随机对话*/
  public final String word;

  /**点击对话组*/
  public final String touchWord;

  /**坐标*/
  public final String coordinate;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SaitamaActionConfig(int id, int people, String action, String beizhu, String word, String touchWord, String coordinate) {
    this.id = id;
    this.people = people;
    this.action = action;
    this.beizhu = beizhu;
    this.word = word;
    this.touchWord = touchWord;
    this.coordinate = coordinate;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
