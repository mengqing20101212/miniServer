package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PatrolMainConfig {
  /**id*/
  public final int id;

  /**所属组别*/
  public final int groupId;

  /**消耗体力*/
  public final int cost;

  /**任务刷新权重*/
  public final int weight;

  /**任务名称*/
  public final String name;

  /**图标*/
  public final int icon;

  /**道具奖励*/
  public final int dropGroupId;

  /**道具预览*/
  public final String itemPre;

  /**人数要求*/
  public final int heroNumLimit;

  /**时间要求（秒？）*/
  public final int timeConsume;

  /**特殊奖励*/
  public final int extraDropGroupId;

  /**特殊奖励初识触发几率*/
  public final int extraDropPro;

  /**等级出现范围*/
  public final String lvLimit;

  /**任务描述*/
  public final String desc;

  /**开始事件*/
  public final int startEvent;

  /**触发事件数量*/
  public final String eventNum;

  /**触发时间随机范围（min）*/
  public final String eventTime;

  /**事件概率*/
  public final String eventPro;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PatrolMainConfig(int id, int groupId, int cost, int weight, String name, int icon, int dropGroupId, String itemPre, int heroNumLimit, int timeConsume, int extraDropGroupId, int extraDropPro, String lvLimit, String desc, int startEvent, String eventNum, String eventTime, String eventPro) {
    this.id = id;
    this.groupId = groupId;
    this.cost = cost;
    this.weight = weight;
    this.name = name;
    this.icon = icon;
    this.dropGroupId = dropGroupId;
    this.itemPre = itemPre;
    this.heroNumLimit = heroNumLimit;
    this.timeConsume = timeConsume;
    this.extraDropGroupId = extraDropGroupId;
    this.extraDropPro = extraDropPro;
    this.lvLimit = lvLimit;
    this.desc = desc;
    this.startEvent = startEvent;
    this.eventNum = eventNum;
    this.eventTime = eventTime;
    this.eventPro = eventPro;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
