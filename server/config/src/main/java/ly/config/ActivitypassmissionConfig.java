package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivitypassmissionConfig {
  /**编号*/
  public final int id;

  /**活动排期*/
  public final int scheDuling;

  /**索引编号*/
  public final int questId;

  /**任务类型*/
  public final int missinType;

  /**最小等级*/
  public final int minLevel;

  /**最大等级*/
  public final int maxLevel;

  /**任务名称*/
  public final String name;

  /**获得积分*/
  public final int point;

  /**跳转*/
  public final int redirectionId;

  /**组ID*/
  public final int goundId;

  /**任务权重*/
  public final int weights;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivitypassmissionConfig(int id, int scheDuling, int questId, int missinType, int minLevel, int maxLevel, String name, int point, int redirectionId, int goundId, int weights) {
    this.id = id;
    this.scheDuling = scheDuling;
    this.questId = questId;
    this.missinType = missinType;
    this.minLevel = minLevel;
    this.maxLevel = maxLevel;
    this.name = name;
    this.point = point;
    this.redirectionId = redirectionId;
    this.goundId = goundId;
    this.weights = weights;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
