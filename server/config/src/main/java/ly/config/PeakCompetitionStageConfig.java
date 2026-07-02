package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PeakCompetitionStageConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String stageName;

  /**组ID*/
  public final int groupId;

  /**下一关id*/
  public final int nextId;

  /**上一关id*/
  public final int lastId;

  /**实际关卡ID*/
  public final int sceneId;

  /**奖励预览*/
  public final int dropShow;

  /**固定角色*/
  public final String fixedRole;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PeakCompetitionStageConfig(int id, String stageName, int groupId, int nextId, int lastId, int sceneId, int dropShow, String fixedRole) {
    this.id = id;
    this.stageName = stageName;
    this.groupId = groupId;
    this.nextId = nextId;
    this.lastId = lastId;
    this.sceneId = sceneId;
    this.dropShow = dropShow;
    this.fixedRole = fixedRole;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
