package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SquareLineConfig {
  /**编号*/
  public final int id;

  /**位置*/
  public final String pos;

  /**方向*/
  public final int direction;

  /**射出速度(ms)*/
  public final int speed;

  /**射出间隔(ms)*/
  public final int interval;

  /**偏移区间*/
  public final String shift;

  /**随机方块池子*/
  public final String square;

  /**特殊效果池子（千分比）*/
  public final String effectPool;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SquareLineConfig(int id, String pos, int direction, int speed, int interval, String shift, String square, String effectPool) {
    this.id = id;
    this.pos = pos;
    this.direction = direction;
    this.speed = speed;
    this.interval = interval;
    this.shift = shift;
    this.square = square;
    this.effectPool = effectPool;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
