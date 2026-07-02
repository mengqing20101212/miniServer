package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivitycashGiftConfig {
  /**ID*/
  public final int id;

  /**GIDs*/
  public final String RecommendedPackage;

  /**等级区间*/
  public final String GradeInterval;

  /**是否是顶部礼包*/
  public final int IsTopGift;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivitycashGiftConfig(int id, String RecommendedPackage, String GradeInterval, int IsTopGift) {
    this.id = id;
    this.RecommendedPackage = RecommendedPackage;
    this.GradeInterval = GradeInterval;
    this.IsTopGift = IsTopGift;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
