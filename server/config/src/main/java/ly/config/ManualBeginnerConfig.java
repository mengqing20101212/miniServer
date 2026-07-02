package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ManualBeginnerConfig {
  /**编号*/
  public final int id;

  /**下一个任务id*/
  public final int nextId;

  /**对应提醒*/
  public final String showWord;

  /**上一个任务*/
  public final int frontId;

  /**fuzhu*/
  public final String fuzhu;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ManualBeginnerConfig(int id, int nextId, String showWord, int frontId, String fuzhu) {
    this.id = id;
    this.nextId = nextId;
    this.showWord = showWord;
    this.frontId = frontId;
    this.fuzhu = fuzhu;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
