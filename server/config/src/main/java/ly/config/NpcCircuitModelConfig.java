package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class NpcCircuitModelConfig {
  /**编号*/
  public final int id;

  /**索引道具id*/
  public final int itemId;

  /**备注*/
  public final String des;

  /**属性类型*/
  public final String type;

  /**套装id*/
  public final int suitId;

  /**品质*/
  public final int quality;

  /**位置*/
  public final int pos;

  /**次序*/
  public final int sequence;

  /**主属性*/
  public final String mainAttr;

  /**初始副属性*/
  public final String startSubAttr;

  /**升级副属性*/
  public final String lvSubAttr;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public NpcCircuitModelConfig(int id, int itemId, String des, String type, int suitId, int quality, int pos, int sequence, String mainAttr, String startSubAttr, String lvSubAttr) {
    this.id = id;
    this.itemId = itemId;
    this.des = des;
    this.type = type;
    this.suitId = suitId;
    this.quality = quality;
    this.pos = pos;
    this.sequence = sequence;
    this.mainAttr = mainAttr;
    this.startSubAttr = startSubAttr;
    this.lvSubAttr = lvSubAttr;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
