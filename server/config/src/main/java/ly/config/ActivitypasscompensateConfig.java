package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivitypasscompensateConfig {
  /**索引ID*/
  public final int id;

  /**活动排期*/
  public final int scheDuling;

  /**需要积分*/
  public final int score;

  /**结算奖励掉落*/
  public final int closeReward;

  /**结算奖励展示*/
  public final int closeRewardShow;

  /**积分继承比例*/
  public final String inherit1;

  /**积分继承比例*/
  public final String inherit2;

  /**积分继承比例*/
  public final String inherit3;

  /**邮件*/
  public final int mail;

  /**进阶购买描述*/
  public final String describe1;

  /**直升购买描述*/
  public final String describe2;

  /**积分宝箱*/
  public final int item;

  /**立绘*/
  public final int picture;

  /**角色立绘坐标偏移缩放*/
  public final String pictureOffset;

  /**购买进阶计划角色立绘坐标偏移缩放*/
  public final String pictureOffset2;

  /**进阶计划奖励展示*/
  public final int passaWard1;

  /**直升计划奖励展示*/
  public final int passaWard2;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivitypasscompensateConfig(int id, int scheDuling, int score, int closeReward, int closeRewardShow, String inherit1, String inherit2, String inherit3, int mail, String describe1, String describe2, int item, int picture, String pictureOffset, String pictureOffset2, int passaWard1, int passaWard2) {
    this.id = id;
    this.scheDuling = scheDuling;
    this.score = score;
    this.closeReward = closeReward;
    this.closeRewardShow = closeRewardShow;
    this.inherit1 = inherit1;
    this.inherit2 = inherit2;
    this.inherit3 = inherit3;
    this.mail = mail;
    this.describe1 = describe1;
    this.describe2 = describe2;
    this.item = item;
    this.picture = picture;
    this.pictureOffset = pictureOffset;
    this.pictureOffset2 = pictureOffset2;
    this.passaWard1 = passaWard1;
    this.passaWard2 = passaWard2;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
