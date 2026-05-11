package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ExDropConfig {
  /**编号*/
  public final int id;

  /**备注*/
  public final String name;

  /**加成参数（百分制）*/
  public final int bonusParam;

  /**来源参数*/
  public final int bonusType;

  /**触发关卡类型*/
  public final String sceneType;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ExDropConfig(int id, String name, int bonusParam, int bonusType, String sceneType) {
    this.id = id;
    this.name = name;
    this.bonusParam = bonusParam;
    this.bonusType = bonusType;
    this.sceneType = sceneType;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
