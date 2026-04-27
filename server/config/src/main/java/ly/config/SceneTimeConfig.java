package ly.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SceneTimeConfig { 
  /**编号*/ 
   public int id;

  /**备注*/ 
   public String des;

  /**重连超时（单位s）*/ 
   public int reConnectTime;

  /**离线判断（单位s）*/ 
   public int offLineTime;

  /**每回合等待（单位s）*/ 
   public int turnTime;

  /**加载超时（单位s）*/ 
   public int playerLoadingTime;

  /**重连超时-组队（单位s）*/ 
   public int reConnectTimeMult;

  /**离线判断-组队（单位s）*/ 
   public int offLineTimeMult;

  /**每回合等待-组队（单位s）*/ 
   public int turnTimeMult;

  /**加载超时-组队（单位s）*/ 
   public int playerLoadingTimeMult;

// @@@@@自定义属性开始区@@@@@ 

 // @@@@@自定义属性结束区@@@@@ 

// @@@@@自定义方法开始区@@@@@ 
public void afterLoad() {}


 // @@@@@自定义方法结束区@@@@@ 

 }
