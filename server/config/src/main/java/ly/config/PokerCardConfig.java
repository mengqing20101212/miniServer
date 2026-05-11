package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PokerCardConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String name;

  /**卡片资源*/
  public final int pic;

  /**裁切*/
  public final String cutUp;

  /**卡片背景*/
  public final int picBase;

  /**英雄类型图片*/
  public final int typePic;

  /**英雄稀有度图片*/
  public final int rarePic;

  /**关联加成列表*/
  public final String bounusList;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PokerCardConfig(int id, String name, int pic, String cutUp, int picBase, int typePic, int rarePic, String bounusList) {
    this.id = id;
    this.name = name;
    this.pic = pic;
    this.cutUp = cutUp;
    this.picBase = picBase;
    this.typePic = typePic;
    this.rarePic = rarePic;
    this.bounusList = bounusList;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
