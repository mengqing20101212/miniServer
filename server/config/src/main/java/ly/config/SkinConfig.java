package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SkinConfig {
  /**id*/
  public final int id;

  /**对应英雄*/
  public final int heroId;

  /**名字*/
  public final String name;

  /**皮肤名称*/
  public final String skinName;

  /**获取途径*/
  public final String skinGet;

  /**跳转ID*/
  public final int turnId;

  /**战斗模型*/
  public final int modelResource;

  /**布阵模型*/
  public final int battleResource;

  /**展示模型*/
  public final int showResource;

  /**英雄表演*/
  public final String heroPerformance;

  /**结束镜头*/
  public final String endPerformance;

  /**主城展示*/
  public final int activityNpc;

  /**512尺寸半身像*/
  public final int cardBust;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SkinConfig(int id, int heroId, String name, String skinName, String skinGet, int turnId, int modelResource, int battleResource, int showResource, String heroPerformance, String endPerformance, int activityNpc, int cardBust) {
    this.id = id;
    this.heroId = heroId;
    this.name = name;
    this.skinName = skinName;
    this.skinGet = skinGet;
    this.turnId = turnId;
    this.modelResource = modelResource;
    this.battleResource = battleResource;
    this.showResource = showResource;
    this.heroPerformance = heroPerformance;
    this.endPerformance = endPerformance;
    this.activityNpc = activityNpc;
    this.cardBust = cardBust;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
