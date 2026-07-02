package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SaitamaRewardConfig {
  /**编号*/
  public final int id;

  /**来源*/
  public final int from;

  /**备注*/
  public final String name;

  /**标题*/
  public final String title;

  /**标题资源ID*/
  public final int titleRes;

  /**资源id*/
  public final int headId;

  /**描述话语*/
  public final String word;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SaitamaRewardConfig(int id, int from, String name, String title, int titleRes, int headId, String word) {
    this.id = id;
    this.from = from;
    this.name = name;
    this.title = title;
    this.titleRes = titleRes;
    this.headId = headId;
    this.word = word;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
