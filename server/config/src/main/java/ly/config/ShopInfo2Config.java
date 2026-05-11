package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ShopInfo2Config {
  /**子商店ID*/
  public final int id;

  /**一级商店id*/
  public final int grade;

  /**二级商店id*/
  public final int school;

  /**子商店名称*/
  public final String name;

  /**商店类别*/
  public final int type;

  /**商店展示类型*/
  public final int showType;

  /**商店参数*/
  public final String shopPara;

  /**商品列表*/
  public final String commodityList;

  /**子商店开启条件类型*/
  public final int openType;

  /**刷新栏类型*/
  public final int RefreshBarType;

  /**开启参数1*/
  public final int openPara1;

  /**开启参数2*/
  public final int openPara2;

  /**子商店开启条件类型2*/
  public final int openType2;

  /**开启参数1*/
  public final int openPara3;

  /**TOP表内id*/
  public final int topId;

  /**背景框资源id*/
  public final int background;

  /**无商品是否显示*/
  public final int subStoreShow;

  /**是否显示商店*/
  public final int shopShow;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ShopInfo2Config(int id, int grade, int school, String name, int type, int showType, String shopPara, String commodityList, int openType, int RefreshBarType, int openPara1, int openPara2, int openType2, int openPara3, int topId, int background, int subStoreShow, int shopShow) {
    this.id = id;
    this.grade = grade;
    this.school = school;
    this.name = name;
    this.type = type;
    this.showType = showType;
    this.shopPara = shopPara;
    this.commodityList = commodityList;
    this.openType = openType;
    this.RefreshBarType = RefreshBarType;
    this.openPara1 = openPara1;
    this.openPara2 = openPara2;
    this.openType2 = openType2;
    this.openPara3 = openPara3;
    this.topId = topId;
    this.background = background;
    this.subStoreShow = subStoreShow;
    this.shopShow = shopShow;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
