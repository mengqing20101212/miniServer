package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class AchievementListConfig {
  /**编号*/
  public final int id;

  /**名字*/
  public final String name;

  /**层级*/
  public final int level;

  /**子成就类列表*/
  public final String subIdList;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public AchievementListConfig(int id, String name, int level, String subIdList) {
    this.id = id;
    this.name = name;
    this.level = level;
    this.subIdList = subIdList;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
