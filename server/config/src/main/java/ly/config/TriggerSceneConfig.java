package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class TriggerSceneConfig {
  /**编号*/
  public final int id;

  /**关卡索引*/
  public final int sceneId;

  /**随机分组*/
  public final int group;

  /**所属类别*/
  public final int type;

  /**副本名称*/
  public final String stageName;

  /**副本名称*/
  public final String stageName2;

  /**副本名称*/
  public final String stageName3;

  /**预先体力消耗*/
  public final int preCost;

  /**胜利后体力消耗*/
  public final int cost;

  /**权重*/
  public final int weight;

  /**出现时间*/
  public final int time;

  /**bossID*/
  public final int bossId;

  /**关卡头像*/
  public final int sceneAvatar;

  /**掉落预览*/
  public final String dropPro;

  /**关卡说明*/
  public final String bossDes;

  /**场景图片id*/
  public final int scenePic;

  /**位置偏移*/
  public final String posOffset;

  /**缩放*/
  public final String scale;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public TriggerSceneConfig(int id, int sceneId, int group, int type, String stageName, String stageName2, String stageName3, int preCost, int cost, int weight, int time, int bossId, int sceneAvatar, String dropPro, String bossDes, int scenePic, String posOffset, String scale) {
    this.id = id;
    this.sceneId = sceneId;
    this.group = group;
    this.type = type;
    this.stageName = stageName;
    this.stageName2 = stageName2;
    this.stageName3 = stageName3;
    this.preCost = preCost;
    this.cost = cost;
    this.weight = weight;
    this.time = time;
    this.bossId = bossId;
    this.sceneAvatar = sceneAvatar;
    this.dropPro = dropPro;
    this.bossDes = bossDes;
    this.scenePic = scenePic;
    this.posOffset = posOffset;
    this.scale = scale;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
