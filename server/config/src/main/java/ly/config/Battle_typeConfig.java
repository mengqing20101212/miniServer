package ly.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class Battle_typeConfig { 
  /**战斗类型描述*/ 
   public String name;

  /**逻辑帧毫秒数*/ 
   public int ms;

  /**心跳掉线时长(秒)*/ 
   public int timeout;

  /**战斗时长(秒)*/ 
   public int battle_time;

  /**战斗大类*/ 
   public int battle_type;

  /**场景id*/ 
   public int sceneid;

  /**准备期总时长秒数*/ 
   public int preparetimeout;

  /**检测超时定时秒数*/ 
   public int checktimeoutinterval;

  /**结算等待秒数*/ 
   public int accounttimeout;

// @@@@@自定义属性开始区@@@@@ 

 // @@@@@自定义属性结束区@@@@@ 

// @@@@@自定义方法开始区@@@@@ 
public void afterLoad() {}


 // @@@@@自定义方法结束区@@@@@ 

 }
