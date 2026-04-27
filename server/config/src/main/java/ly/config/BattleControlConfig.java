package ly.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class BattleControlConfig { 
  /**编号*/ 
   public int id;

  /**活动名称*/ 
   public String name;

  /**阵容id*/ 
   public int lineupId;

  /**是否是服务器战斗*/ 
   public int isOnlineBattle;

  /**在线战斗通用LOADING界面最大等待时间*/ 
   public int loadingTime;

  /**战前准备时间限制*/ 
   public int readyTime;

  /**战前换阵容时间限制（单位s）*/ 
   public int changeHeroTime;

  /**时间是否共用*/ 
   public int isShare;

  /**每回合行动时间限制（单位s）*/ 
   public int chaoiceWaitTime;

  /**是否保存战斗数据*/ 
   public int isSaveDate;

// @@@@@自定义属性开始区@@@@@ 

 // @@@@@自定义属性结束区@@@@@ 

// @@@@@自定义方法开始区@@@@@ 
public void afterLoad() {}


 // @@@@@自定义方法结束区@@@@@ 

 }
