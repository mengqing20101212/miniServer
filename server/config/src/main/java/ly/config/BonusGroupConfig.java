package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class BonusGroupConfig {
  /**编号*/
  public final int id;

  /**Bonus列表*/
  public final String bonusList;

  /**Bonus组产生概率*/
  public final String bonusPro;

  /**Bonus组产生次数*/
  public final String bonusTimes;

  /**组内相对概率*/
  public final String bonusRelativePro;

  /**备注*/
  public final String beizhu;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public BonusGroupConfig(int id, String bonusList, String bonusPro, String bonusTimes, String bonusRelativePro, String beizhu) {
    this.id = id;
    this.bonusList = bonusList;
    this.bonusPro = bonusPro;
    this.bonusTimes = bonusTimes;
    this.bonusRelativePro = bonusRelativePro;
    this.beizhu = beizhu;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
