package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ChapterRewardConfig {
  /**编号*/
  public final int id;

  /**关卡ID*/
  public final int StageId;

  /**奖励类型*/
  public final int type;

  /**奖励展示*/
  public final String rewardShow;

  /**实际掉落*/
  public final int drop;

  /**外显文字1*/
  public final String word1;

  /**外显文字2*/
  public final String word2;

  /**外显渐变颜色(,)*/
  public final String outGradientColor;

  /**内显文字1*/
  public final String word3;

  /**内显文字2*/
  public final String word4;

  /**内显渐变颜色(,)*/
  public final String inGradientColor;

  /**内显文字3*/
  public final String word5;

  /**图片显示 atlas*/
  public final int icon;

  /**不可领取时是否弹出*/
  public final int popUp;

  /**弹出界面中的图片显示(texture)*/
  public final int popUpUIIcon;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ChapterRewardConfig(int id, int StageId, int type, String rewardShow, int drop, String word1, String word2, String outGradientColor, String word3, String word4, String inGradientColor, String word5, int icon, int popUp, int popUpUIIcon) {
    this.id = id;
    this.StageId = StageId;
    this.type = type;
    this.rewardShow = rewardShow;
    this.drop = drop;
    this.word1 = word1;
    this.word2 = word2;
    this.outGradientColor = outGradientColor;
    this.word3 = word3;
    this.word4 = word4;
    this.inGradientColor = inGradientColor;
    this.word5 = word5;
    this.icon = icon;
    this.popUp = popUp;
    this.popUpUIIcon = popUpUIIcon;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
