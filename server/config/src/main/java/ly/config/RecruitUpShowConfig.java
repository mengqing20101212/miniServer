package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class RecruitUpShowConfig {
  /**招募编号*/
  public final int id;

  /**活动排期*/
  public final int scheDuling;

  /**碎片显示图片*/
  public final int piecePicResId;

  /**招募角色id*/
  public final int heroId;

  /**英雄名称*/
  public final String heroName;

  /**招募说明文本图片*/
  public final int contentPicResId;

  /**跳转类型*/
  public final int jumpType;

  /**试玩关卡ID*/
  public final int sceneId;

  /**英雄显示样式模板*/
  public final int heroShowStyleType;

  /**英雄显示图片(可能有多个)*/
  public final String heroPicResId;

  /**英雄按钮跳转id(根据样式改变)*/
  public final String heroJumpId;

  /**视频名称*/
  public final String videoName;

  /**视频界面中英雄图片*/
  public final int videoHeroPic;

  /**视频界面中英雄信息图片*/
  public final int videoHeroInfoPic;

  /**视频界面中英雄图片的坐标偏移*/
  public final String videoHeroPicPosOffset;

  /**是否显示全息logo*/
  public final int isShowHolograpicLogo;

  /**是否显示返场英雄*/
  public final int isShowReturnLogo;

  /**礼包图标*/
  public final int gifBagIcon;

  /**礼包跳转*/
  public final int gifBagTurn;

  /**招募跳转*/
  public final int turnId;

  /**对应的活动id*/
  public final int trueActivityId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public RecruitUpShowConfig(int id, int scheDuling, int piecePicResId, int heroId, String heroName, int contentPicResId, int jumpType, int sceneId, int heroShowStyleType, String heroPicResId, String heroJumpId, String videoName, int videoHeroPic, int videoHeroInfoPic, String videoHeroPicPosOffset, int isShowHolograpicLogo, int isShowReturnLogo, int gifBagIcon, int gifBagTurn, int turnId, int trueActivityId) {
    this.id = id;
    this.scheDuling = scheDuling;
    this.piecePicResId = piecePicResId;
    this.heroId = heroId;
    this.heroName = heroName;
    this.contentPicResId = contentPicResId;
    this.jumpType = jumpType;
    this.sceneId = sceneId;
    this.heroShowStyleType = heroShowStyleType;
    this.heroPicResId = heroPicResId;
    this.heroJumpId = heroJumpId;
    this.videoName = videoName;
    this.videoHeroPic = videoHeroPic;
    this.videoHeroInfoPic = videoHeroInfoPic;
    this.videoHeroPicPosOffset = videoHeroPicPosOffset;
    this.isShowHolograpicLogo = isShowHolograpicLogo;
    this.isShowReturnLogo = isShowReturnLogo;
    this.gifBagIcon = gifBagIcon;
    this.gifBagTurn = gifBagTurn;
    this.turnId = turnId;
    this.trueActivityId = trueActivityId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
