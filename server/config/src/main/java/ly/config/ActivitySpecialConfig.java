package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivitySpecialConfig {
  /**编号*/
  public final int id;

  /**活动排期*/
  public final int scheDuling;

  /**活动说明*/
  public final String name;

  /**英雄显示样式模板*/
  public final int heroShowStyleType;

  /**角色立绘*/
  public final int rolePic;

  /**角色立绘坐标偏移*/
  public final String rolePicPosOffset;

  /**角色立绘缩放偏移*/
  public final String rolePicScaleOffset;

  /**活动标题*/
  public final int titleResID;

  /**背景图*/
  public final int bgPic;

  /**全息标记*/
  public final int holographic;

  /**对应的活动id*/
  public final int trueActivityId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivitySpecialConfig(int id, int scheDuling, String name, int heroShowStyleType, int rolePic, String rolePicPosOffset, String rolePicScaleOffset, int titleResID, int bgPic, int holographic, int trueActivityId) {
    this.id = id;
    this.scheDuling = scheDuling;
    this.name = name;
    this.heroShowStyleType = heroShowStyleType;
    this.rolePic = rolePic;
    this.rolePicPosOffset = rolePicPosOffset;
    this.rolePicScaleOffset = rolePicScaleOffset;
    this.titleResID = titleResID;
    this.bgPic = bgPic;
    this.holographic = holographic;
    this.trueActivityId = trueActivityId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
