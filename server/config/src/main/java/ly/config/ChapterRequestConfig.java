package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ChapterRequestConfig {
  /**编号*/
  public final int id;

  /**类型*/
  public final int patterntype;

  /**内容*/
  public final String content;

  /**备注*/
  public final String beizhu;

  /**关卡掉落*/
  public final int dropGroup;

  /**首通掉落*/
  public final int firstDrop;

  /**协助目标*/
  public final String target;

  /**协助结果*/
  public final String result;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ChapterRequestConfig(int id, int patterntype, String content, String beizhu, int dropGroup, int firstDrop, String target, String result) {
    this.id = id;
    this.patterntype = patterntype;
    this.content = content;
    this.beizhu = beizhu;
    this.dropGroup = dropGroup;
    this.firstDrop = firstDrop;
    this.target = target;
    this.result = result;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
