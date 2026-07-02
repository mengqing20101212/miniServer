package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class BattleFieldConfig {
  /**编号*/
  public final int id;

  /**我方点位List*/
  public final String posListRed;

  /**敌方点位List*/
  public final String posListBlue;

  /**我方全体前*/
  public final String frontAllRed;

  /**敌方全体前*/
  public final String frontAllBlue;

  /**我方战场中心*/
  public final String BFCenterRed;

  /**敌方战场中心*/
  public final String BFCenterBlue;

  /**战场中心*/
  public final String BFCenter;

  /**选择目标镜头list我方*/
  public final String cameraListRed;

  /**选择目标镜头list敌方*/
  public final String cameraListBlue;

  /**我方点位List*/
  public final String posListRedSummon;

  /**敌方点位List*/
  public final String posListBlueSummon;

  /**场景镜头配置*/
  public final String sceneConfig;

  /**场景配置敌方*/
  public final String sceneConfigEnemy;

  /**我方站位连线类型*/
  public final int lineupType;

  /**敌方站位连线类型*/
  public final int lineupTypeEnemy;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public BattleFieldConfig(int id, String posListRed, String posListBlue, String frontAllRed, String frontAllBlue, String BFCenterRed, String BFCenterBlue, String BFCenter, String cameraListRed, String cameraListBlue, String posListRedSummon, String posListBlueSummon, String sceneConfig, String sceneConfigEnemy, int lineupType, int lineupTypeEnemy) {
    this.id = id;
    this.posListRed = posListRed;
    this.posListBlue = posListBlue;
    this.frontAllRed = frontAllRed;
    this.frontAllBlue = frontAllBlue;
    this.BFCenterRed = BFCenterRed;
    this.BFCenterBlue = BFCenterBlue;
    this.BFCenter = BFCenter;
    this.cameraListRed = cameraListRed;
    this.cameraListBlue = cameraListBlue;
    this.posListRedSummon = posListRedSummon;
    this.posListBlueSummon = posListBlueSummon;
    this.sceneConfig = sceneConfig;
    this.sceneConfigEnemy = sceneConfigEnemy;
    this.lineupType = lineupType;
    this.lineupTypeEnemy = lineupTypeEnemy;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
