package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityTaskConfig {
  /**编号*/
  public final int id;

  /**任务标题*/
  public final String title;

  /**任务名称*/
  public final String name;

  /**任务类型*/
  public final int questType;

  /**活动排期*/
  public final int scheDuling;

  /**所属页签*/
  public final int page;

  /**内容类型*/
  public final int condition;

  /**奖励展示*/
  public final String rewardShow;

  /**实际掉落*/
  public final int drop;

  /**跳转*/
  public final int redirectionId;

  /**展示优先级*/
  public final int priority;

  /**积分类型*/
  public final int pointType;

  /**获得积分*/
  public final int point;

  /**开始时间*/
  public final String startTime;

  /**结束时间*/
  public final String endTime;

  /**任务标签图片*/
  public final int titlePicId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityTaskConfig(int id, String title, String name, int questType, int scheDuling, int page, int condition, String rewardShow, int drop, int redirectionId, int priority, int pointType, int point, String startTime, String endTime, int titlePicId) {
    this.id = id;
    this.title = title;
    this.name = name;
    this.questType = questType;
    this.scheDuling = scheDuling;
    this.page = page;
    this.condition = condition;
    this.rewardShow = rewardShow;
    this.drop = drop;
    this.redirectionId = redirectionId;
    this.priority = priority;
    this.pointType = pointType;
    this.point = point;
    this.startTime = startTime;
    this.endTime = endTime;
    this.titlePicId = titlePicId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
