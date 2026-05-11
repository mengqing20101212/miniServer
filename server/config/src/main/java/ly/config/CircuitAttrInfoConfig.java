package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class CircuitAttrInfoConfig {
  /**编号*/
  public final int id;

  /**类型*/
  public final int type;

  /**描述*/
  public final String description;

  /**技能描述*/
  public final String skillDescription;

  /**属性类型*/
  public final int attrType;

  /**属性数值*/
  public final int attrNum;

  /**升级增量*/
  public final int upgradeAdd;

  /**回路名称*/
  public final String name;

  /**位置*/
  public final int pos;

  /**品质*/
  public final String quality;

  /**次序*/
  public final int sequence;

  /**附带技能Id*/
  public final int skillId;

  /**扩展技能Id*/
  public final int extraSkillId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public CircuitAttrInfoConfig(int id, int type, String description, String skillDescription, int attrType, int attrNum, int upgradeAdd, String name, int pos, String quality, int sequence, int skillId, int extraSkillId) {
    this.id = id;
    this.type = type;
    this.description = description;
    this.skillDescription = skillDescription;
    this.attrType = attrType;
    this.attrNum = attrNum;
    this.upgradeAdd = upgradeAdd;
    this.name = name;
    this.pos = pos;
    this.quality = quality;
    this.sequence = sequence;
    this.skillId = skillId;
    this.extraSkillId = extraSkillId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
