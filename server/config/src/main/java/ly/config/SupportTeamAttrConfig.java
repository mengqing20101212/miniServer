package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SupportTeamAttrConfig {
  /**编号（应援类型*10000+应援等级）*/
  public final int id;

  /**进阶数*/
  public final int advance;

  /**下一阶段编号*/
  public final int nextId;

  /**备注*/
  public final String description;

  /**属性类型*/
  public final String attrType;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SupportTeamAttrConfig(int id, int advance, int nextId, String description, String attrType) {
    this.id = id;
    this.advance = advance;
    this.nextId = nextId;
    this.description = description;
    this.attrType = attrType;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
