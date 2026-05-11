package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ItemSynthesisConfig {
  /**编号*/
  public final int id;

  /**新道具名字*/
  public final String newName;

  /**原道具id*/
  public final int oldItem;

  /**原道具名字*/
  public final String oldName;

  /**需求原道具数量*/
  public final int needNum;

  /**道具级别*/
  public final int level;

  /**每次合成成本道具id*/
  public final int costItem;

  /**每次合成成本道具数量*/
  public final int costNum;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ItemSynthesisConfig(int id, String newName, int oldItem, String oldName, int needNum, int level, int costItem, int costNum) {
    this.id = id;
    this.newName = newName;
    this.oldItem = oldItem;
    this.oldName = oldName;
    this.needNum = needNum;
    this.level = level;
    this.costItem = costItem;
    this.costNum = costNum;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
