package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class CircuitItemInfoConfig {
  /**编号*/
  public final int id;

  /**回路描述*/
  public final String description;

  /**类型*/
  public final int type;

  /**一级分类*/
  public final int school;

  /**二级分类*/
  public final int grade;

  /**三级分类*/
  public final int sequence;

  /**回路位置*/
  public final int pos;

  /**套装Id*/
  public final String suitIds;

  /**主属性随机池ID*/
  public final int mainAttrPoolId;

  /**初始副属性随机池ID*/
  public final int subAttrPoolId;

  /**副属性随机池ID*/
  public final int subAttrUpgradePoolId;

  /**升级模板*/
  public final int upgradeType;

  /**拆分钞票*/
  public final int decomposeGold;

  /**拆分掉落组*/
  public final int decomposeDG;

  /**品质*/
  public final int quality;

  /**闪光属性ID*/
  public final int lightAttrPoolId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public CircuitItemInfoConfig(int id, String description, int type, int school, int grade, int sequence, int pos, String suitIds, int mainAttrPoolId, int subAttrPoolId, int subAttrUpgradePoolId, int upgradeType, int decomposeGold, int decomposeDG, int quality, int lightAttrPoolId) {
    this.id = id;
    this.description = description;
    this.type = type;
    this.school = school;
    this.grade = grade;
    this.sequence = sequence;
    this.pos = pos;
    this.suitIds = suitIds;
    this.mainAttrPoolId = mainAttrPoolId;
    this.subAttrPoolId = subAttrPoolId;
    this.subAttrUpgradePoolId = subAttrUpgradePoolId;
    this.upgradeType = upgradeType;
    this.decomposeGold = decomposeGold;
    this.decomposeDG = decomposeDG;
    this.quality = quality;
    this.lightAttrPoolId = lightAttrPoolId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
