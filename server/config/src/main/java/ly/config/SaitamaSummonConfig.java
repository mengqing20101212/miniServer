package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SaitamaSummonConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String name;

  /**卡片种类*/
  public final int type;

  /**等级*/
  public final int level;

  /**图标*/
  public final int icon;

  /**条件展示文字*/
  public final String showWord;

  /**使用效果文字*/
  public final String useEffect;

  /**战斗中计数文字*/
  public final String countWord;

  /**演示技能id*/
  public final int eventID;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SaitamaSummonConfig(int id, String name, int type, int level, int icon, String showWord, String useEffect, String countWord, int eventID) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.level = level;
    this.icon = icon;
    this.showWord = showWord;
    this.useEffect = useEffect;
    this.countWord = countWord;
    this.eventID = eventID;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
