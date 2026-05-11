package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ContactGameConfig {
  /**编号*/
  public final int id;

  /**标题*/
  public final String title;

  /**时间（s）*/
  public final int missionList;

  /**任务目标*/
  public final int targetNum;

  /**失败允许次数*/
  public final int missNum;

  /**目标标签*/
  public final int targetTag;

  /**正确池子*/
  public final String rightPool;

  /**正确池子随机数量*/
  public final int randomNum;

  /**池子*/
  public final String pool;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ContactGameConfig(int id, String title, int missionList, int targetNum, int missNum, int targetTag, String rightPool, int randomNum, String pool) {
    this.id = id;
    this.title = title;
    this.missionList = missionList;
    this.targetNum = targetNum;
    this.missNum = missNum;
    this.targetTag = targetTag;
    this.rightPool = rightPool;
    this.randomNum = randomNum;
    this.pool = pool;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
