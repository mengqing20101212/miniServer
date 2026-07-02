package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class HeroAIWeightConfig {
  /**英雄编号*/
  public final int heroId;

  /**模板编号*/
  public final int modelId;

  /**备注*/
  public final String name;

  /**备注*/
  public final String des;

  /**优先级*/
  public final int CLASS;

  /**次序*/
  public final int sequence;

  /**权重*/
  public final int weight;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public HeroAIWeightConfig(int heroId, int modelId, String name, String des, int CLASS, int sequence, int weight) {
    this.heroId = heroId;
    this.modelId = modelId;
    this.name = name;
    this.des = des;
    this.CLASS = CLASS;
    this.sequence = sequence;
    this.weight = weight;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
