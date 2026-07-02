package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DreamMainNewConfig {
  /**编号*/
  public final int id;

  /**下一层id*/
  public final int nextId;

  /**名字*/
  public final String name;

  /**名字资源id*/
  public final int nameResource;

  /**背景图*/
  public final int bgResource;

  /**需要门票*/
  public final int ticketId;

  /**门票数量*/
  public final int ticketNum;

  /**奖励*/
  public final String dropList;

  /**包含关卡*/
  public final String sceneList;

  /**可上阵英雄数量*/
  public final String heroCount;

  /**关卡机制*/
  public final String mechanism;

  /**英雄限定第1关*/
  public final String limit1;

  /**英雄限定第2关*/
  public final String limit2;

  /**英雄限定第3关*/
  public final String limit3;

  /**英雄限定第4关*/
  public final String limit4;

  /**奖励显示*/
  public final int dropShow;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DreamMainNewConfig(int id, int nextId, String name, int nameResource, int bgResource, int ticketId, int ticketNum, String dropList, String sceneList, String heroCount, String mechanism, String limit1, String limit2, String limit3, String limit4, int dropShow) {
    this.id = id;
    this.nextId = nextId;
    this.name = name;
    this.nameResource = nameResource;
    this.bgResource = bgResource;
    this.ticketId = ticketId;
    this.ticketNum = ticketNum;
    this.dropList = dropList;
    this.sceneList = sceneList;
    this.heroCount = heroCount;
    this.mechanism = mechanism;
    this.limit1 = limit1;
    this.limit2 = limit2;
    this.limit3 = limit3;
    this.limit4 = limit4;
    this.dropShow = dropShow;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
