package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class AchievementMainConfig {
  /**编号*/
  public final int id;

  /**成就组*/
  public final int group;

  /**成就组（服务器记录用）*/
  public final int groupServer;

  /**是否同组*/
  public final int isIfGroup;

  /**组级别*/
  public final int groupLv;

  /**展示优先级*/
  public final int priority;

  /**一级分类*/
  public final int firstType;

  /**二级分类*/
  public final int secondType;

  /**成就名称*/
  public final String name;

  /**成就描述*/
  public final String des;

  /**任务Id*/
  public final int missionId;

  /**积分*/
  public final int point;

  /**奖励预览*/
  public final String rewardShow;

  /**实际奖励*/
  public final int dropId;

  /**index*/
  public final int index;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public AchievementMainConfig(int id, int group, int groupServer, int isIfGroup, int groupLv, int priority, int firstType, int secondType, String name, String des, int missionId, int point, String rewardShow, int dropId, int index) {
    this.id = id;
    this.group = group;
    this.groupServer = groupServer;
    this.isIfGroup = isIfGroup;
    this.groupLv = groupLv;
    this.priority = priority;
    this.firstType = firstType;
    this.secondType = secondType;
    this.name = name;
    this.des = des;
    this.missionId = missionId;
    this.point = point;
    this.rewardShow = rewardShow;
    this.dropId = dropId;
    this.index = index;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
