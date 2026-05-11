package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SceneConfig {
  /**编号*/
  public final int id;

  /**场景配置*/
  public final String sceneConfig;

  /**场景配置*/
  public final String sceneConfigEnemy;

  /**场景名称*/
  public final String resname;

  /**后期效果*/
  public final int profile;

  /**场景类型*/
  public final int tpye;

  /**场景雾配置*/
  public final String fogConfig;

  /**场景光配置*/
  public final String lightConfig;

  /**功能挂点信息*/
  public final String functionPoint;

  /**挂点推镜配置*/
  public final String functionCameraConfig;

  /**场景角色光影设置*/
  public final String roleLight;

  /**胜利动作位置*/
  public final String WinPos;

  /**后期特效资源*/
  public final int eftResid;

  /**后期特效资源偏移*/
  public final String eftResOffset;

  /**场景音效(非BGM)*/
  public final String SFX;

  /**场景镜头转动*/
  public final int sceneConfigRotation;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SceneConfig(int id, String sceneConfig, String sceneConfigEnemy, String resname, int profile, int tpye, String fogConfig, String lightConfig, String functionPoint, String functionCameraConfig, String roleLight, String WinPos, int eftResid, String eftResOffset, String SFX, int sceneConfigRotation) {
    this.id = id;
    this.sceneConfig = sceneConfig;
    this.sceneConfigEnemy = sceneConfigEnemy;
    this.resname = resname;
    this.profile = profile;
    this.tpye = tpye;
    this.fogConfig = fogConfig;
    this.lightConfig = lightConfig;
    this.functionPoint = functionPoint;
    this.functionCameraConfig = functionCameraConfig;
    this.roleLight = roleLight;
    this.WinPos = WinPos;
    this.eftResid = eftResid;
    this.eftResOffset = eftResOffset;
    this.SFX = SFX;
    this.sceneConfigRotation = sceneConfigRotation;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
