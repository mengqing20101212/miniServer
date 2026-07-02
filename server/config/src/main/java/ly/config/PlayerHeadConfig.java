package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PlayerHeadConfig {
  /**编号*/
  public final int id;

  /**名字*/
  public final String name;

  /**描述*/
  public final String des;

  /**头像*/
  public final int icon;

  /**解锁条件*/
  public final int unlockType;

  /**解锁参数*/
  public final String unlockPara;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PlayerHeadConfig(int id, String name, String des, int icon, int unlockType, String unlockPara) {
    this.id = id;
    this.name = name;
    this.des = des;
    this.icon = icon;
    this.unlockType = unlockType;
    this.unlockPara = unlockPara;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
