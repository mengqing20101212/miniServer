package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SuitInfoConfig {
  /**套装Id*/
  public final int suitId;

  /**套装名字*/
  public final String suitName;

  /**套装图标*/
  public final int suitIcon;

  /**套装大图标*/
  public final int suitIcon2;

  /**套装激活*/
  public final String suitActive;

  /**2件套简称*/
  public final String twoSuitDec;

  /**3件套简称*/
  public final String threeSuitDec;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SuitInfoConfig(int suitId, String suitName, int suitIcon, int suitIcon2, String suitActive, String twoSuitDec, String threeSuitDec) {
    this.suitId = suitId;
    this.suitName = suitName;
    this.suitIcon = suitIcon;
    this.suitIcon2 = suitIcon2;
    this.suitActive = suitActive;
    this.twoSuitDec = twoSuitDec;
    this.threeSuitDec = threeSuitDec;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
