package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SocietyPurchaseConfig {
  /**编号*/
  public final int id;

  /**等级段*/
  public final int phase;

  /**次序*/
  public final int sequence;

  /**最小等级*/
  public final int minLevel;

  /**最大等级*/
  public final int maxLevel;

  /**稀有道具标记*/
  public final int isRare;

  /**需求道具*/
  public final int demandProps;

  /**道具名称*/
  public final String beizhu1;

  /**需求数量*/
  public final int demandNum;

  /**每个道具奖励*/
  public final String eachReward;

  /**协助额外奖励*/
  public final String additionalReward;

  /**随机权重*/
  public final int weights;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SocietyPurchaseConfig(int id, int phase, int sequence, int minLevel, int maxLevel, int isRare, int demandProps, String beizhu1, int demandNum, String eachReward, String additionalReward, int weights) {
    this.id = id;
    this.phase = phase;
    this.sequence = sequence;
    this.minLevel = minLevel;
    this.maxLevel = maxLevel;
    this.isRare = isRare;
    this.demandProps = demandProps;
    this.beizhu1 = beizhu1;
    this.demandNum = demandNum;
    this.eachReward = eachReward;
    this.additionalReward = additionalReward;
    this.weights = weights;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
