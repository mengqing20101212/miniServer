package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityfallConfig {
  /**索引ID*/
  public final int id;

  /**任务类型*/
  public final int questType;

  /**备注*/
  public final String beizhu;

  /**掉落道具*/
  public final String fallitem;

  /**掉落道具显示*/
  public final String item;

  /**跳转*/
  public final int turn;

  /**活动标题*/
  public final String title;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityfallConfig(int id, int questType, String beizhu, String fallitem, String item, int turn, String title) {
    this.id = id;
    this.questType = questType;
    this.beizhu = beizhu;
    this.fallitem = fallitem;
    this.item = item;
    this.turn = turn;
    this.title = title;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
