package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SaitamaActionpoolConfig {
  /**编号*/
  public final int id;

  /**是否生效*/
  public final int isEffect;

  /**权重*/
  public final int weight;

  /**随机口味*/
  public final int flavor;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SaitamaActionpoolConfig(int id, int isEffect, int weight, int flavor) {
    this.id = id;
    this.isEffect = isEffect;
    this.weight = weight;
    this.flavor = flavor;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
