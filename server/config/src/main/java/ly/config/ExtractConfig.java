package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ExtractConfig {
  /**英雄*/
  public final int id;

  /**备注*/
  public final String name;

  /**等级*/
  public final int level;

  /**排行*/
  public final String ranking;

  /**排名*/
  public final int rankNum;

  /**城市*/
  public final String city;

  /**宣言*/
  public final String lines;

  /**随机展示*/
  public final int random;

  /**角色半身像*/
  public final int bodyPic;

  /**简短的描述*/
  public final String features;

  /**人物招募动作*/
  public final String timeline;

  /**招募动画背景*/
  public final int bg;

  /**英文名*/
  public final String englishName;

  /**角色小图片资源Id(目前不用)*/
  public final int smallPicResId;

  /**招募英雄位置*/
  public final String smallPicPosOffset;

  /**招募英雄角度*/
  public final String smallPicRotationOffset;

  /**招募英雄缩放*/
  public final String smallPicScale;

  /**英雄品质底框*/
  public final int bodyQualityBgResId;

  /**名字底框*/
  public final int nameQualityBgResId;

  /**重复播放英雄展示动画*/
  public final int isRepeatPlay;

  /**英雄模型背景预设*/
  public final int backgroundId;

  /**分享使用立绘*/
  public final int shareAnimation;

  /**分享立绘缩放*/
  public final String sharePicScale;

  /**分享立绘位置*/
  public final String sharePicPosOffset;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ExtractConfig(int id, String name, int level, String ranking, int rankNum, String city, String lines, int random, int bodyPic, String features, String timeline, int bg, String englishName, int smallPicResId, String smallPicPosOffset, String smallPicRotationOffset, String smallPicScale, int bodyQualityBgResId, int nameQualityBgResId, int isRepeatPlay, int backgroundId, int shareAnimation, String sharePicScale, String sharePicPosOffset) {
    this.id = id;
    this.name = name;
    this.level = level;
    this.ranking = ranking;
    this.rankNum = rankNum;
    this.city = city;
    this.lines = lines;
    this.random = random;
    this.bodyPic = bodyPic;
    this.features = features;
    this.timeline = timeline;
    this.bg = bg;
    this.englishName = englishName;
    this.smallPicResId = smallPicResId;
    this.smallPicPosOffset = smallPicPosOffset;
    this.smallPicRotationOffset = smallPicRotationOffset;
    this.smallPicScale = smallPicScale;
    this.bodyQualityBgResId = bodyQualityBgResId;
    this.nameQualityBgResId = nameQualityBgResId;
    this.isRepeatPlay = isRepeatPlay;
    this.backgroundId = backgroundId;
    this.shareAnimation = shareAnimation;
    this.sharePicScale = sharePicScale;
    this.sharePicPosOffset = sharePicPosOffset;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
