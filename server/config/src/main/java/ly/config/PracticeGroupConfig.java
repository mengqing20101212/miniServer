package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PracticeGroupConfig {
  /**编号*/
  public final int id;

  /**名称*/
  public final String name;

  /**资源图*/
  public final int resource;

  /**展示顺序*/
  public final int show;

  /**关卡*/
  public final String stage;

  /**解锁条件*/
  public final int unlock;

  /**解锁内容*/
  public final String unlockText;

  /**品质*/
  public final int quality;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PracticeGroupConfig(int id, String name, int resource, int show, String stage, int unlock, String unlockText, int quality) {
    this.id = id;
    this.name = name;
    this.resource = resource;
    this.show = show;
    this.stage = stage;
    this.unlock = unlock;
    this.unlockText = unlockText;
    this.quality = quality;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
