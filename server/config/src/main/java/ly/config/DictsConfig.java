package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DictsConfig {
  /**id*/
  public final int id;

  /**备注*/
  public final String desc;

  /**中文文本*/
  public final String name_cn;

  /**英文文本*/
  public final String name_en;

  /**备注英文文本*/
  public final String desc_en;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DictsConfig(int id, String desc, String name_cn, String name_en, String desc_en) {
    this.id = id;
    this.desc = desc;
    this.name_cn = name_cn;
    this.name_en = name_en;
    this.desc_en = desc_en;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
