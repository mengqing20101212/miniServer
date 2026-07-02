package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class HitWordConfig {
  /**ID*/
  public final int id;

  /**名称*/
  public final String name;

  /**初始位移偏移*/
  public final String initialOffset;

  /**初始大小*/
  public final String initialScale;

  /**初始透明度*/
  public final String initialAlpha;

  /**显现的大小*/
  public final String appearScale;

  /**显现的透明度*/
  public final String appearAlpha;

  /**初始到显现的时间*/
  public final String toAppearTime;

  /**显现持续时间*/
  public final String appearLastTime;

  /**消失时的目标大小*/
  public final String disappearScale;

  /**消失时的透明度大小*/
  public final String disappearAlpha;

  /**消失时间*/
  public final String disappearTime;

  /**下一个点偏移*/
  public final String nextPosOffset;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public HitWordConfig(int id, String name, String initialOffset, String initialScale, String initialAlpha, String appearScale, String appearAlpha, String toAppearTime, String appearLastTime, String disappearScale, String disappearAlpha, String disappearTime, String nextPosOffset) {
    this.id = id;
    this.name = name;
    this.initialOffset = initialOffset;
    this.initialScale = initialScale;
    this.initialAlpha = initialAlpha;
    this.appearScale = appearScale;
    this.appearAlpha = appearAlpha;
    this.toAppearTime = toAppearTime;
    this.appearLastTime = appearLastTime;
    this.disappearScale = disappearScale;
    this.disappearAlpha = disappearAlpha;
    this.disappearTime = disappearTime;
    this.nextPosOffset = nextPosOffset;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
