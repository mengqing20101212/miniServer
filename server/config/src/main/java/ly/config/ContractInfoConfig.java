package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ContractInfoConfig {
  /**编号*/
  public final int id;

  /**品质保底类型*/
  public final int welfareId;

  /**招募类型*/
  public final int contractType;

  /**招募道具*/
  public final int item;

  /**消耗数量*/
  public final int num;

  /**招募次数*/
  public final int contractNum;

  /**对应掉落组*/
  public final int dropId;

  /**累计次数掉落组*/
  public final String sumAwardId;

  /**注释*/
  public final String desc;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ContractInfoConfig(int id, int welfareId, int contractType, int item, int num, int contractNum, int dropId, String sumAwardId, String desc) {
    this.id = id;
    this.welfareId = welfareId;
    this.contractType = contractType;
    this.item = item;
    this.num = num;
    this.contractNum = contractNum;
    this.dropId = dropId;
    this.sumAwardId = sumAwardId;
    this.desc = desc;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
