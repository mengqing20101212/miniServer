package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PeakCompetitionAssemblyConfig {
  /**编号*/
  public final int id;

  /**组件名称*/
  public final String name;

  /**类型*/
  public final int type;

  /**技能效果*/
  public final int skillID;

  /**属性类型*/
  public final int attrType;

  /**属性参数*/
  public final int attrNum;

  /**固定星级*/
  public final int heroAdvance;

  /**固定技能等级*/
  public final int skillLv;

  /**穿戴限制角色品质*/
  public final String quality;

  /**穿戴限制*/
  public final int limit;

  /**效果描述*/
  public final String description;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PeakCompetitionAssemblyConfig(int id, String name, int type, int skillID, int attrType, int attrNum, int heroAdvance, int skillLv, String quality, int limit, String description) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.skillID = skillID;
    this.attrType = attrType;
    this.attrNum = attrNum;
    this.heroAdvance = heroAdvance;
    this.skillLv = skillLv;
    this.quality = quality;
    this.limit = limit;
    this.description = description;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
