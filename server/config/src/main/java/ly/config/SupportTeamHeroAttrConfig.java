package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SupportTeamHeroAttrConfig {
  /**编号*/
  public final int id;

  /**属性类别*/
  public final int attrClass;

  /**加成等级*/
  public final int attrLevel;

  /**升级消耗道具数量*/
  public final int upCostItemNum;

  /**加成属性*/
  public final String attrType;

  /**属性名称*/
  public final String attrName;

  /**是否解锁*/
  public final int unlcok;

  /**属性图标*/
  public final String icon;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SupportTeamHeroAttrConfig(int id, int attrClass, int attrLevel, int upCostItemNum, String attrType, String attrName, int unlcok, String icon) {
    this.id = id;
    this.attrClass = attrClass;
    this.attrLevel = attrLevel;
    this.upCostItemNum = upCostItemNum;
    this.attrType = attrType;
    this.attrName = attrName;
    this.unlcok = unlcok;
    this.icon = icon;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
