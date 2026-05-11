package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class CreditConfig {
  /**编号*/
  public final int id;

  /**功能ID*/
  public final int activityList;

  /**场景ID*/
  public final String sceneId;

  /**分组ID*/
  public final int grouoId;

  /**门槛下限*/
  public final int ScoreMin;

  /**门槛上限*/
  public final int ScoreMax;

  /**标签*/
  public final String lable;

  /**标签下限*/
  public final int lableMin;

  /**标签上限*/
  public final int lableMax;

  /**提示*/
  public final String dec;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public CreditConfig(int id, int activityList, String sceneId, int grouoId, int ScoreMin, int ScoreMax, String lable, int lableMin, int lableMax, String dec) {
    this.id = id;
    this.activityList = activityList;
    this.sceneId = sceneId;
    this.grouoId = grouoId;
    this.ScoreMin = ScoreMin;
    this.ScoreMax = ScoreMax;
    this.lable = lable;
    this.lableMin = lableMin;
    this.lableMax = lableMax;
    this.dec = dec;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
