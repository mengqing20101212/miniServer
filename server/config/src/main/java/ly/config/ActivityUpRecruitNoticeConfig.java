package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityUpRecruitNoticeConfig {
  /**编号*/
  public final int id;

  /**活动排期*/
  public final int scheDuling;

  /**名称*/
  public final String name;

  /**英雄显示样式模板*/
  public final int heroShowStyleType;

  /**角色立绘*/
  public final int rolePic;

  /**角色立绘坐标偏移*/
  public final String rolePicPosOffset;

  /**角色立绘缩放偏移*/
  public final String rolePicScaleOffset;

  /**获取描述文本图片id*/
  public final int getDesPicResId;

  /**技能描述图片id1*/
  public final int skillPicResId1;

  /**技能描述图片1坐标*/
  public final String skillPicResId1PosOffset;

  /**技能描述图片id2*/
  public final int skillPicResId2;

  /**技能描述图片2坐标*/
  public final String skillPicResId2PosOffset;

  /**跳转类型*/
  public final int jumpType;

  /**试玩关卡ID*/
  public final int sceneId;

  /**抽卡说明（SSR概率小字）*/
  public final int drawCardDecPic;

  /**背景图*/
  public final int bgPic;

  /**背景中角色小图(多个)*/
  public final String bgHeroPics;

  /**小图立绘坐标偏移*/
  public final String bgPicPosOffset;

  /**小图立绘缩放偏移*/
  public final String bgPicScaleOffset;

  /**倒计时图片(1,2,3,...)*/
  public final String numberPic;

  /**对应卡池活动ID*/
  public final int activityId;

  /**对应的活动id*/
  public final int trueActivityId;

  /**全息标记*/
  public final int holographic;

  /**是否显示特效*/
  public final int SpecialEffects;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityUpRecruitNoticeConfig(int id, int scheDuling, String name, int heroShowStyleType, int rolePic, String rolePicPosOffset, String rolePicScaleOffset, int getDesPicResId, int skillPicResId1, String skillPicResId1PosOffset, int skillPicResId2, String skillPicResId2PosOffset, int jumpType, int sceneId, int drawCardDecPic, int bgPic, String bgHeroPics, String bgPicPosOffset, String bgPicScaleOffset, String numberPic, int activityId, int trueActivityId, int holographic, int SpecialEffects) {
    this.id = id;
    this.scheDuling = scheDuling;
    this.name = name;
    this.heroShowStyleType = heroShowStyleType;
    this.rolePic = rolePic;
    this.rolePicPosOffset = rolePicPosOffset;
    this.rolePicScaleOffset = rolePicScaleOffset;
    this.getDesPicResId = getDesPicResId;
    this.skillPicResId1 = skillPicResId1;
    this.skillPicResId1PosOffset = skillPicResId1PosOffset;
    this.skillPicResId2 = skillPicResId2;
    this.skillPicResId2PosOffset = skillPicResId2PosOffset;
    this.jumpType = jumpType;
    this.sceneId = sceneId;
    this.drawCardDecPic = drawCardDecPic;
    this.bgPic = bgPic;
    this.bgHeroPics = bgHeroPics;
    this.bgPicPosOffset = bgPicPosOffset;
    this.bgPicScaleOffset = bgPicScaleOffset;
    this.numberPic = numberPic;
    this.activityId = activityId;
    this.trueActivityId = trueActivityId;
    this.holographic = holographic;
    this.SpecialEffects = SpecialEffects;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
