package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class LevelHintConfig {
  /**ID（任务唯一id）*/
  public final int id;

  /**图标*/
  public final int icon;

  /**提示*/
  public final String hint;

  /**描述*/
  public final String des;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public LevelHintConfig(int id, int icon, String hint, String des) {
    this.id = id;
    this.icon = icon;
    this.hint = hint;
    this.des = des;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
