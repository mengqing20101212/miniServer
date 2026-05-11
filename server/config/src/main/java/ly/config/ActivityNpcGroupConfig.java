package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityNpcGroupConfig {
  /**编号*/
  public final int id;

  /**默认动作*/
  public final String defaultAni;

  /**辅助列*/
  public final String p1;

  /**备注是谁*/
  public final String groupName;

  /**点击反馈动作组*/
  public final String clickAniList;

  /**推镜的镜头左右偏移（正是左负是右,左右是角度，上下是距离）*/
  public final String moveDistance;

  /**注视点高度*/
  public final String cameraHight;

  /**推镜的距离配置*/
  public final String cameraDistance;

  /**角色距离*/
  public final String npcDistance;

  /**文本框的效果类型*/
  public final String textPrefabType;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityNpcGroupConfig(int id, String defaultAni, String p1, String groupName, String clickAniList, String moveDistance, String cameraHight, String cameraDistance, String npcDistance, String textPrefabType) {
    this.id = id;
    this.defaultAni = defaultAni;
    this.p1 = p1;
    this.groupName = groupName;
    this.clickAniList = clickAniList;
    this.moveDistance = moveDistance;
    this.cameraHight = cameraHight;
    this.cameraDistance = cameraDistance;
    this.npcDistance = npcDistance;
    this.textPrefabType = textPrefabType;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
