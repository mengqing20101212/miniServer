package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class TeamBattleConfig {
  public final int id;

  public final int type;

  public final int activityId;

  public final int level;

  public final String name;

  public final int dungeonConfigType;

  public final int dungeonConfigId;

  public final int sceneId;

  public final boolean isDynamic;

  public final int isOut;

  public final int stageType;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public TeamBattleConfig(int id, int type, int activityId, int level, String name, int dungeonConfigType, int dungeonConfigId, int sceneId, boolean isDynamic, int isOut, int stageType) {
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
