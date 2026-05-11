package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class JusticeMainConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String name;

  /**包含关卡组*/
  public final String stageGroup;

  /**关卡组奖励*/
  public final int dropId;

  /**奖励预览*/
  public final String dropShow;

  /**下一区域*/
  public final int nextZone;

  /**上一区域*/
  public final int lastZone;

  /**背景图*/
  public final int background;

  /**完成区域时的小背景图(目前可以直接用背景图一样即可)*/
  public final int finishSmallBackground;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public JusticeMainConfig(int id, String name, String stageGroup, int dropId, String dropShow, int nextZone, int lastZone, int background, int finishSmallBackground) {
    this.id = id;
    this.name = name;
    this.stageGroup = stageGroup;
    this.dropId = dropId;
    this.dropShow = dropShow;
    this.nextZone = nextZone;
    this.lastZone = lastZone;
    this.background = background;
    this.finishSmallBackground = finishSmallBackground;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
