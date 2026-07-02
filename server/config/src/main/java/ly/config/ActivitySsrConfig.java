package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivitySsrConfig {
  /**编号*/
  public final int id;

  /**类别*/
  public final int tpye;

  /**积分奖励展示*/
  public final String rewardShow;

  /**对应任务ID*/
  public final int activityTaskId;

  /**活动排期*/
  public final int scheDuling;

  /**名字*/
  public final String name;

  /**立绘*/
  public final int picture;

  /**坐标*/
  public final String coordinate;

  /**背景图*/
  public final int bg;

  /**长宽*/
  public final String size;

  /**阴影坐标*/
  public final String coordinateS;

  /**阴影长宽*/
  public final String sizeS;

  /**半透坐标*/
  public final String coordinateH;

  /**半透长宽*/
  public final String sizeH;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivitySsrConfig(int id, int tpye, String rewardShow, int activityTaskId, int scheDuling, String name, int picture, String coordinate, int bg, String size, String coordinateS, String sizeS, String coordinateH, String sizeH) {
    this.id = id;
    this.tpye = tpye;
    this.rewardShow = rewardShow;
    this.activityTaskId = activityTaskId;
    this.scheDuling = scheDuling;
    this.name = name;
    this.picture = picture;
    this.coordinate = coordinate;
    this.bg = bg;
    this.size = size;
    this.coordinateS = coordinateS;
    this.sizeS = sizeS;
    this.coordinateH = coordinateH;
    this.sizeH = sizeH;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
