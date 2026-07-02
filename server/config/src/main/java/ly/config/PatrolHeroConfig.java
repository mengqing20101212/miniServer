package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PatrolHeroConfig {
  /**id*/
  public final int id;

  /**名字*/
  public final String name;

  /**工作感言*/
  public final String workSentiment;

  /**工作日期*/
  public final String workDay;

  /**工作日期描述*/
  public final String workDayDesc;

  /**羁绊英雄*/
  public final int heroFetter;

  /**基础掉落加成*/
  public final int baseAdd;

  /**额外掉落触发几率提升*/
  public final int extraAdd;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PatrolHeroConfig(int id, String name, String workSentiment, String workDay, String workDayDesc, int heroFetter, int baseAdd, int extraAdd) {
    this.id = id;
    this.name = name;
    this.workSentiment = workSentiment;
    this.workDay = workDay;
    this.workDayDesc = workDayDesc;
    this.heroFetter = heroFetter;
    this.baseAdd = baseAdd;
    this.extraAdd = extraAdd;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
