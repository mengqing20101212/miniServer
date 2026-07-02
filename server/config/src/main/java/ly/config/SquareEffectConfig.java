package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SquareEffectConfig {
  /**编号*/
  public final int id;

  /**备注*/
  public final String name;

  /**数量*/
  public final int count;

  /**参数*/
  public final int para;

  /**攻击特效资源id*/
  public final int attackEffect;

  /**受击特效资源id*/
  public final int hitEffect;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SquareEffectConfig(int id, String name, int count, int para, int attackEffect, int hitEffect) {
    this.id = id;
    this.name = name;
    this.count = count;
    this.para = para;
    this.attackEffect = attackEffect;
    this.hitEffect = hitEffect;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
