package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PokerCardConfig {
  public final int id;

  public final String name;

  public final int pic;

  public final String cutUp;

  public final int picBase;

  public final int typePic;

  public final int rarePic;

  public final java.util.List<Integer> bounusList;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PokerCardConfig(int id, String name, int pic, String cutUp, int picBase, int typePic, int rarePic, java.util.List<Integer> bounusList) {
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
