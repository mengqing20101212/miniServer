package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityUpRecruitShowConfig {
  /**编号*/
  public final int id;

  /**活动排期*/
  public final int scheDuling;

  /**英雄显示样式模板*/
  public final int heroShowStyleType;

  /**名称*/
  public final String name;

  /**名称2*/
  public final String name2;

  /**名称坐标*/
  public final String namePos;

  /**名称坐标2*/
  public final String namePos2;

  /**角色立绘*/
  public final int rolePic;

  /**角色立绘坐标偏移*/
  public final String rolePicPosOffset;

  /**角色立绘缩放偏移*/
  public final String rolePicScaleOffset;

  /**角色立绘2*/
  public final int rolePic2;

  /**角色立绘坐标偏移2*/
  public final String rolePicPosOffset2;

  /**角色立绘缩放偏移2*/
  public final String rolePicScaleOffset2;

  /**获取描述文本图片id*/
  public final int getDesPicResId;

  /**获取描述文本*/
  public final String getDesText;

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

  /**立即前往ID*/
  public final int turnId;

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

  /**全息标记*/
  public final int holographic;

  /**对应的活动id*/
  public final int trueActivityId;

  /**是否显示特效*/
  public final int SpecialEffects;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityUpRecruitShowConfig(int id, int scheDuling, int heroShowStyleType, String name, String name2, String namePos, String namePos2, int rolePic, String rolePicPosOffset, String rolePicScaleOffset, int rolePic2, String rolePicPosOffset2, String rolePicScaleOffset2, int getDesPicResId, String getDesText, int skillPicResId1, String skillPicResId1PosOffset, int skillPicResId2, String skillPicResId2PosOffset, int jumpType, int sceneId, int turnId, int drawCardDecPic, int bgPic, String bgHeroPics, String bgPicPosOffset, String bgPicScaleOffset, int holographic, int trueActivityId, int SpecialEffects) {
    this.id = id;
    this.scheDuling = scheDuling;
    this.heroShowStyleType = heroShowStyleType;
    this.name = name;
    this.name2 = name2;
    this.namePos = namePos;
    this.namePos2 = namePos2;
    this.rolePic = rolePic;
    this.rolePicPosOffset = rolePicPosOffset;
    this.rolePicScaleOffset = rolePicScaleOffset;
    this.rolePic2 = rolePic2;
    this.rolePicPosOffset2 = rolePicPosOffset2;
    this.rolePicScaleOffset2 = rolePicScaleOffset2;
    this.getDesPicResId = getDesPicResId;
    this.getDesText = getDesText;
    this.skillPicResId1 = skillPicResId1;
    this.skillPicResId1PosOffset = skillPicResId1PosOffset;
    this.skillPicResId2 = skillPicResId2;
    this.skillPicResId2PosOffset = skillPicResId2PosOffset;
    this.jumpType = jumpType;
    this.sceneId = sceneId;
    this.turnId = turnId;
    this.drawCardDecPic = drawCardDecPic;
    this.bgPic = bgPic;
    this.bgHeroPics = bgHeroPics;
    this.bgPicPosOffset = bgPicPosOffset;
    this.bgPicScaleOffset = bgPicScaleOffset;
    this.holographic = holographic;
    this.trueActivityId = trueActivityId;
    this.SpecialEffects = SpecialEffects;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
