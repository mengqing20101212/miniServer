package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class HeroAdvanceConfig {
  /**编号*/
  public final int id;

  /**英雄模板名*/
  public final int modelName;

  /**进阶次序*/
  public final int sequence;

  /**属性类型*/
  public final String attrType;

  /**属性数值*/
  public final String attrNum;

  /**技能点*/
  public final int skillPoint;

  /**技能点数量*/
  public final int skillPointNum;

  /**进阶材料需求*/
  public final String advanceItem;

  /**进阶替换物*/
  public final int replaceItems;

  /**属性描述*/
  public final String describe;

  /**获取途径道具ID*/
  public final int getItem;

  /**掉落ID*/
  public final int drop;

  /**掉落展示*/
  public final int dropShow;

  /**物品描述*/
  public final String itemDes;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public HeroAdvanceConfig(int id, int modelName, int sequence, String attrType, String attrNum, int skillPoint, int skillPointNum, String advanceItem, int replaceItems, String describe, int getItem, int drop, int dropShow, String itemDes) {
    this.id = id;
    this.modelName = modelName;
    this.sequence = sequence;
    this.attrType = attrType;
    this.attrNum = attrNum;
    this.skillPoint = skillPoint;
    this.skillPointNum = skillPointNum;
    this.advanceItem = advanceItem;
    this.replaceItems = replaceItems;
    this.describe = describe;
    this.getItem = getItem;
    this.drop = drop;
    this.dropShow = dropShow;
    this.itemDes = itemDes;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
