package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ScenePointConfig {
  /**id*/
  public final int id;

  /**场景id*/
  public final int sceneId;

  /**挂点集*/
  public final String scenePointList;

  /**角色配置*/
  public final String pointRoleList;

  /**挂点推镜配置*/
  public final String functionCameraConfig;

  /**挂点集*/
  public final String freeScenePointList;

  /**挂点推镜配置*/
  public final String freeCameraConfig;

  /**特殊配置*/
  public final String Param_1;

  /**组合挂点集*/
  public final String groupScenePointList;

  /**组合挂点推镜配置*/
  public final String groupCameraConfig;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ScenePointConfig(int id, int sceneId, String scenePointList, String pointRoleList, String functionCameraConfig, String freeScenePointList, String freeCameraConfig, String Param_1, String groupScenePointList, String groupCameraConfig) {
    this.id = id;
    this.sceneId = sceneId;
    this.scenePointList = scenePointList;
    this.pointRoleList = pointRoleList;
    this.functionCameraConfig = functionCameraConfig;
    this.freeScenePointList = freeScenePointList;
    this.freeCameraConfig = freeCameraConfig;
    this.Param_1 = Param_1;
    this.groupScenePointList = groupScenePointList;
    this.groupCameraConfig = groupCameraConfig;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
