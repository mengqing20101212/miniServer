package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SocietyCleanCityRepairConfig {
  /**编号*/
  public final int id;

  /**事件组*/
  public final int group;

  /**上交的物品*/
  public final String payItem;

  /**奖励组*/
  public final int drop;

  /**提交文本*/
  public final String desc;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SocietyCleanCityRepairConfig(int id, int group, String payItem, int drop, String desc) {
    this.id = id;
    this.group = group;
    this.payItem = payItem;
    this.drop = drop;
    this.desc = desc;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
