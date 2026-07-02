package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SocietyQuestConfig {
  /**编号*/
  public final int id;

  /**索引编号*/
  public final int questId;

  /**索引编号2*/
  public final int questId2;

  /**最小等级*/
  public final int minLevel;

  /**最大等级*/
  public final int maxLevel;

  /**任务标题*/
  public final String title;

  /**任务名称*/
  public final String name;

  /**任务名称2*/
  public final String name2;

  /**任务星级*/
  public final int star;

  /**奖励类型*/
  public final int rewardType;

  /**物品名称*/
  public final String beizhu1;

  /**任务权重*/
  public final int weights;

  /**协作标记*/
  public final int isCooperate;

  /**稀有标记*/
  public final int isRare;

  /**奖励展示*/
  public final String rewardShow;

  /**实际掉落*/
  public final int drop;

  /**beizhu2*/
  public final String beizhu2;

  /**beizhu3*/
  public final String beizhu3;

  /**beizhu4*/
  public final String beizhu4;

  /**beizhu5*/
  public final String beizhu5;

  /**跳转*/
  public final int redirectionId;

  /**跳转*/
  public final int redirectionId2;

  /**组ID*/
  public final int goundId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SocietyQuestConfig(int id, int questId, int questId2, int minLevel, int maxLevel, String title, String name, String name2, int star, int rewardType, String beizhu1, int weights, int isCooperate, int isRare, String rewardShow, int drop, String beizhu2, String beizhu3, String beizhu4, String beizhu5, int redirectionId, int redirectionId2, int goundId) {
    this.id = id;
    this.questId = questId;
    this.questId2 = questId2;
    this.minLevel = minLevel;
    this.maxLevel = maxLevel;
    this.title = title;
    this.name = name;
    this.name2 = name2;
    this.star = star;
    this.rewardType = rewardType;
    this.beizhu1 = beizhu1;
    this.weights = weights;
    this.isCooperate = isCooperate;
    this.isRare = isRare;
    this.rewardShow = rewardShow;
    this.drop = drop;
    this.beizhu2 = beizhu2;
    this.beizhu3 = beizhu3;
    this.beizhu4 = beizhu4;
    this.beizhu5 = beizhu5;
    this.redirectionId = redirectionId;
    this.redirectionId2 = redirectionId2;
    this.goundId = goundId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
