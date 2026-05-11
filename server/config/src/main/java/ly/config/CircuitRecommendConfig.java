package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class CircuitRecommendConfig {
  /**编号*/
  public final int id;

  /**名字*/
  public final String name;

  /**推荐回路效果1*/
  public final String recommend1;

  /**推荐回路效果2*/
  public final String recommend2;

  /**推荐回路属性1*/
  public final String recommendAttr1;

  /**推荐回路属性2*/
  public final String recommendAttr2;

  /**推荐回路属性3*/
  public final String recommendAttr3;

  /**推荐源核文字*/
  public final String recommendWord;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public CircuitRecommendConfig(int id, String name, String recommend1, String recommend2, String recommendAttr1, String recommendAttr2, String recommendAttr3, String recommendWord) {
    this.id = id;
    this.name = name;
    this.recommend1 = recommend1;
    this.recommend2 = recommend2;
    this.recommendAttr1 = recommendAttr1;
    this.recommendAttr2 = recommendAttr2;
    this.recommendAttr3 = recommendAttr3;
    this.recommendWord = recommendWord;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
