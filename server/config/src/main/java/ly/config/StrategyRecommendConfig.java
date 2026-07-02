package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class StrategyRecommendConfig {
  /**id(这里也当做角色Id)*/
  public final int id;

  /**推荐阵容（,）*/
  public final String lineupGroupIds;

  /**关键思路原核（,）*/
  public final String keySourceIds;

  /**关键思路的描述*/
  public final String keyThinkingDetail;

  /**推荐原核（,）*/
  public final String recommendSourceIds;

  /**角色玩法详情描述*/
  public final String heroPlayDetail;

  /**关键技能1描述（包括技能名称）*/
  public final String skillDetail;

  /**角色名字*/
  public final String heroName;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public StrategyRecommendConfig(int id, String lineupGroupIds, String keySourceIds, String keyThinkingDetail, String recommendSourceIds, String heroPlayDetail, String skillDetail, String heroName) {
    this.id = id;
    this.lineupGroupIds = lineupGroupIds;
    this.keySourceIds = keySourceIds;
    this.keyThinkingDetail = keyThinkingDetail;
    this.recommendSourceIds = recommendSourceIds;
    this.heroPlayDetail = heroPlayDetail;
    this.skillDetail = skillDetail;
    this.heroName = heroName;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
