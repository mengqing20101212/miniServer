package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class Battle_typeConfig {
  /**战斗类型描述*/
  public final String name;

  /**逻辑帧毫秒数*/
  public final int ms;

  /**心跳掉线时长(秒)*/
  public final int timeout;

  /**战斗时长(秒)*/
  public final int battle_time;

  /**战斗大类*/
  public final int battle_type;

  /**场景id*/
  public final int sceneid;

  /**准备期总时长秒数*/
  public final int preparetimeout;

  /**检测超时定时秒数*/
  public final int checktimeoutinterval;

  /**结算等待秒数*/
  public final int accounttimeout;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public Battle_typeConfig(String name, int ms, int timeout, int battle_time, int battle_type, int sceneid, int preparetimeout, int checktimeoutinterval, int accounttimeout) {
    this.name = name;
    this.ms = ms;
    this.timeout = timeout;
    this.battle_time = battle_time;
    this.battle_type = battle_type;
    this.sceneid = sceneid;
    this.preparetimeout = preparetimeout;
    this.checktimeoutinterval = checktimeoutinterval;
    this.accounttimeout = accounttimeout;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
