package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SaitamaGuestConfig {
  /**编号*/
  public final int id;

  /**出现概率（百分比）*/
  public final int probability;

  /**随机权重（废弃）*/
  public final int weight;

  /**奖励id*/
  public final int dropId;

  /**名称*/
  public final String name;

  /**模型*/
  public final String model;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SaitamaGuestConfig(int id, int probability, int weight, int dropId, String name, String model) {
    this.id = id;
    this.probability = probability;
    this.weight = weight;
    this.dropId = dropId;
    this.name = name;
    this.model = model;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
