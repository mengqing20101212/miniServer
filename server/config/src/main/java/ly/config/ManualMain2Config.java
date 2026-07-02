package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ManualMain2Config {
  /**编号*/
  public final int id;

  /**任务名称*/
  public final String dailyNum;

  /**所属页签*/
  public final int page;

  /**任务展示编号*/
  public final String showNum;

  /**展示优先级*/
  public final int priority;

  /**任务类型*/
  public final int questType;

  /**内容类型*/
  public final int condition;

  /**权重*/
  public final int weight;

  /**奖励展示*/
  public final String rewardShow;

  /**实际掉落*/
  public final int drop;

  /**跳转*/
  public final int redirectionId;

  /**主界面显示文本*/
  public final String dailyNum2;

  /**图标显示名*/
  public final String nameIcon;

  /**功能图标*/
  public final String dailyIcon;

  /**任务是否随机*/
  public final int typeIcon;

  /**显示等级*/
  public final int lvShow;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ManualMain2Config(int id, String dailyNum, int page, String showNum, int priority, int questType, int condition, int weight, String rewardShow, int drop, int redirectionId, String dailyNum2, String nameIcon, String dailyIcon, int typeIcon, int lvShow) {
    this.id = id;
    this.dailyNum = dailyNum;
    this.page = page;
    this.showNum = showNum;
    this.priority = priority;
    this.questType = questType;
    this.condition = condition;
    this.weight = weight;
    this.rewardShow = rewardShow;
    this.drop = drop;
    this.redirectionId = redirectionId;
    this.dailyNum2 = dailyNum2;
    this.nameIcon = nameIcon;
    this.dailyIcon = dailyIcon;
    this.typeIcon = typeIcon;
    this.lvShow = lvShow;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
