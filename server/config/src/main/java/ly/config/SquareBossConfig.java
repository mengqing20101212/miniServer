package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SquareBossConfig {
  /**编号*/
  public final int id;

  /**boss名称*/
  public final String name;

  /**出现权重*/
  public final int weight;

  /**血量*/
  public final int bossHp;

  /**技能释放时机(ms)*/
  public final String skillTime;

  /**技能蓄力时间(ms)*/
  public final int chargeTime;

  /**蓄力展示时间段*/
  public final String chargeShowTime;

  /**boss盾牌区间*/
  public final String shield;

  /**技能参数*/
  public final String skillPara;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SquareBossConfig(int id, String name, int weight, int bossHp, String skillTime, int chargeTime, String chargeShowTime, String shield, String skillPara) {
    this.id = id;
    this.name = name;
    this.weight = weight;
    this.bossHp = bossHp;
    this.skillTime = skillTime;
    this.chargeTime = chargeTime;
    this.chargeShowTime = chargeShowTime;
    this.shield = shield;
    this.skillPara = skillPara;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
