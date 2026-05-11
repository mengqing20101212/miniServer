package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class CookMaterialConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String name;

  /**类型*/
  public final int type;

  /**种类*/
  public final int classify;

  /**参数*/
  public final int para;

  /**星级*/
  public final int star;

  /**展示顺序*/
  public final int showId;

  /**价值量*/
  public final int price;

  /**作用描述*/
  public final String note;

  /**奖励id*/
  public final int rewardId;

  /**奖励数量*/
  public final int rewardNum;

  /**好感度值*/
  public final int friendNum;

  /**暴击增加奖励数量*/
  public final int critRewardNum;

  /**暴击好感度增加值*/
  public final int critFriendNum;

  /**口味奖励数量*/
  public final int exRewardNum;

  /**口味奖励好感度*/
  public final int exFriendNum;

  /**qte奖励数量*/
  public final String qteRewardNum;

  /**qte奖励好感度*/
  public final String qteFriendNum;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public CookMaterialConfig(int id, String name, int type, int classify, int para, int star, int showId, int price, String note, int rewardId, int rewardNum, int friendNum, int critRewardNum, int critFriendNum, int exRewardNum, int exFriendNum, String qteRewardNum, String qteFriendNum) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.classify = classify;
    this.para = para;
    this.star = star;
    this.showId = showId;
    this.price = price;
    this.note = note;
    this.rewardId = rewardId;
    this.rewardNum = rewardNum;
    this.friendNum = friendNum;
    this.critRewardNum = critRewardNum;
    this.critFriendNum = critFriendNum;
    this.exRewardNum = exRewardNum;
    this.exFriendNum = exFriendNum;
    this.qteRewardNum = qteRewardNum;
    this.qteFriendNum = qteFriendNum;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
