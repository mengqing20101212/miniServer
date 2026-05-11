package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class TeamBattleConfig {
  /**编号*/
  public final int id;

  /**副本创建类型*/
  public final int type;

  /**活动id*/
  public final int activityId;

  /**活动难度*/
  public final int level;

  /**选项名字*/
  public final String name;

  /**检索表*/
  public final int dungeonConfigType;

  /**advancedStage表ID*/
  public final int dungeonConfigId;

  /**关卡id*/
  public final int sceneId;

  /**是否动态运算*/
  public final String isDynamic;

  /**是否断线离队*/
  public final int isOut;

  /**关卡类型*/
  public final int stageType;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public TeamBattleConfig(int id, int type, int activityId, int level, String name, int dungeonConfigType, int dungeonConfigId, int sceneId, String isDynamic, int isOut, int stageType) {
    this.id = id;
    this.type = type;
    this.activityId = activityId;
    this.level = level;
    this.name = name;
    this.dungeonConfigType = dungeonConfigType;
    this.dungeonConfigId = dungeonConfigId;
    this.sceneId = sceneId;
    this.isDynamic = isDynamic;
    this.isOut = isOut;
    this.stageType = stageType;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
