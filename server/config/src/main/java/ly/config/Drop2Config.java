package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class Drop2Config {
  /**编号*/
  public final int id;

  /**掉落类型*/
  public final int dropType;

  /**备注*/
  public final String des;

  /**掉落列表*/
  public final String dropList;

  /**组概率(绝对概率)*/
  public final String itemPro;

  /**组内概率(相对概率)*/
  public final String itemRelativePro;

  /**掉落次数*/
  public final String dropTimes;

  /**掉落数量*/
  public final String dropCounts;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public Drop2Config(int id, int dropType, String des, String dropList, String itemPro, String itemRelativePro, String dropTimes, String dropCounts) {
    this.id = id;
    this.dropType = dropType;
    this.des = des;
    this.dropList = dropList;
    this.itemPro = itemPro;
    this.itemRelativePro = itemRelativePro;
    this.dropTimes = dropTimes;
    this.dropCounts = dropCounts;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
