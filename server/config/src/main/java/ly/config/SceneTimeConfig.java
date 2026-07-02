package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SceneTimeConfig {
  /**编号*/
  public final int id;

  /**备注*/
  public final String des;

  /**重连超时（单位s）*/
  public final int reConnectTime;

  /**离线判断（单位s）*/
  public final int offLineTime;

  /**每回合等待（单位s）*/
  public final int turnTime;

  /**加载超时（单位s）*/
  public final int playerLoadingTime;

  /**重连超时-组队（单位s）*/
  public final int reConnectTimeMult;

  /**离线判断-组队（单位s）*/
  public final int offLineTimeMult;

  /**每回合等待-组队（单位s）*/
  public final int turnTimeMult;

  /**加载超时-组队（单位s）*/
  public final int playerLoadingTimeMult;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SceneTimeConfig(int id, String des, int reConnectTime, int offLineTime, int turnTime, int playerLoadingTime, int reConnectTimeMult, int offLineTimeMult, int turnTimeMult, int playerLoadingTimeMult) {
    this.id = id;
    this.des = des;
    this.reConnectTime = reConnectTime;
    this.offLineTime = offLineTime;
    this.turnTime = turnTime;
    this.playerLoadingTime = playerLoadingTime;
    this.reConnectTimeMult = reConnectTimeMult;
    this.offLineTimeMult = offLineTimeMult;
    this.turnTimeMult = turnTimeMult;
    this.playerLoadingTimeMult = playerLoadingTimeMult;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
