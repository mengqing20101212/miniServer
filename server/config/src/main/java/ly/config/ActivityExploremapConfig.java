package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityExploremapConfig {
  /**序号*/
  public final int id;

  /**期数*/
  public final int scheDuling;

  /**层数*/
  public final int layers;

  /**消耗探索值*/
  public final int Exploreid;

  /**探索值icon*/
  public final int pictureid;

  /**特殊图片*/
  public final String specialPicture;

  /**区域文字*/
  public final String regionalText1;

  /**区域文字*/
  public final String regionalText2;

  /**地图坐标*/
  public final String mapCoordinates;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityExploremapConfig(int id, int scheDuling, int layers, int Exploreid, int pictureid, String specialPicture, String regionalText1, String regionalText2, String mapCoordinates) {
    this.id = id;
    this.scheDuling = scheDuling;
    this.layers = layers;
    this.Exploreid = Exploreid;
    this.pictureid = pictureid;
    this.specialPicture = specialPicture;
    this.regionalText1 = regionalText1;
    this.regionalText2 = regionalText2;
    this.mapCoordinates = mapCoordinates;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
