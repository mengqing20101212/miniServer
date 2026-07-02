package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class HeroStoryStatusConfig {
  /**编号*/
  public final int id;

  /**属性类型*/
  public final int statusType;

  /**加成文字*/
  public final String effectWord;

  /**加成参数类型*/
  public final int paraType;

  /**参数*/
  public final int para;

  /**属性图标*/
  public final int statusShow;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public HeroStoryStatusConfig(int id, int statusType, String effectWord, int paraType, int para, int statusShow) {
    this.id = id;
    this.statusType = statusType;
    this.effectWord = effectWord;
    this.paraType = paraType;
    this.para = para;
    this.statusShow = statusShow;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
