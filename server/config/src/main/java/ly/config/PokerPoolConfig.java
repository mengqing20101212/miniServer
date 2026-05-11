package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PokerPoolConfig {
  /**id*/
  public final int id;

  /**备注*/
  public final String name;

  /**随机数量*/
  public final int randomNumber;

  /**随机池*/
  public final String pool;

  /**妨碍池*/
  public final String hinder;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PokerPoolConfig(int id, String name, int randomNumber, String pool, String hinder) {
    this.id = id;
    this.name = name;
    this.randomNumber = randomNumber;
    this.pool = pool;
    this.hinder = hinder;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
