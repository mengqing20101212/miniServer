package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ShopType2Config {
  /**ID*/
  public final int id;

  /**商店名称*/
  public final String name;

  /**商店类别*/
  public final int type;

  /**子商店列表*/
  public final String subShopList;

  /**商店开启条件类型*/
  public final int openType;

  /**开启参数1*/
  public final int openPara1;

  /**开启参数2*/
  public final int openPara2;

  /**是否出现在商店里*/
  public final int isShow;

  /**显示排序*/
  public final int showSort;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ShopType2Config(int id, String name, int type, String subShopList, int openType, int openPara1, int openPara2, int isShow, int showSort) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.subShopList = subShopList;
    this.openType = openType;
    this.openPara1 = openPara1;
    this.openPara2 = openPara2;
    this.isShow = isShow;
    this.showSort = showSort;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
