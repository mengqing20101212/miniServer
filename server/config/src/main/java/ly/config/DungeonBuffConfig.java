package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DungeonBuffConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String name;

  /**品质*/
  public final int kind;

  /**所属组*/
  public final int group;

  /**buff等级*/
  public final int level;

  /**持续回合*/
  public final int turn;

  /**效果枚举*/
  public final int effectType;

  /**实际buffId*/
  public final int buffId;

  /**描述*/
  public final String discribe;

  /**buff图标*/
  public final int icon;

  /**buff大图标*/
  public final int iconBig;

  /**buff类型底板*/
  public final int floor;

  /**buff类型边框*/
  public final int frame;

  /**buff类型icon*/
  public final int heroType;

  /**组件*/
  public final int assembly;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DungeonBuffConfig(int id, String name, int kind, int group, int level, int turn, int effectType, int buffId, String discribe, int icon, int iconBig, int floor, int frame, int heroType, int assembly) {
    this.id = id;
    this.name = name;
    this.kind = kind;
    this.group = group;
    this.level = level;
    this.turn = turn;
    this.effectType = effectType;
    this.buffId = buffId;
    this.discribe = discribe;
    this.icon = icon;
    this.iconBig = iconBig;
    this.floor = floor;
    this.frame = frame;
    this.heroType = heroType;
    this.assembly = assembly;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
