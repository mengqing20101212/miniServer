package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DreamMainConfig {
  /**编号*/
  public final int id;

  /**名字*/
  public final String name;

  /**名字资源id*/
  public final int nameResource;

  /**标题资源id*/
  public final int titleResource;

  /**特效资源id*/
  public final int effectResource;

  /**类型*/
  public final int type;

  /**需要门票*/
  public final int ticketId;

  /**门票数量*/
  public final int ticketNum;

  /**事件随机池*/
  public final String eventPool;

  /**背景图*/
  public final int bgResource;

  /**奖励预览*/
  public final String rewardList;

  /**新奖励预览*/
  public final String dropShow;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DreamMainConfig(int id, String name, int nameResource, int titleResource, int effectResource, int type, int ticketId, int ticketNum, String eventPool, int bgResource, String rewardList, String dropShow) {
    this.id = id;
    this.name = name;
    this.nameResource = nameResource;
    this.titleResource = titleResource;
    this.effectResource = effectResource;
    this.type = type;
    this.ticketId = ticketId;
    this.ticketNum = ticketNum;
    this.eventPool = eventPool;
    this.bgResource = bgResource;
    this.rewardList = rewardList;
    this.dropShow = dropShow;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
