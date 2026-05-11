package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SocietyCleanCityStoryConfig {
  /**编号*/
  public final int id;

  /**玩法类型*/
  public final int type;

  /**事件组*/
  public final int group;

  /**玩法参数*/
  public final int para;

  /**题目池*/
  public final String questionPool;

  /**成功奖励*/
  public final int dropSuccess;

  /**失败奖励*/
  public final int dropFail;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SocietyCleanCityStoryConfig(int id, int type, int group, int para, String questionPool, int dropSuccess, int dropFail) {
    this.id = id;
    this.type = type;
    this.group = group;
    this.para = para;
    this.questionPool = questionPool;
    this.dropSuccess = dropSuccess;
    this.dropFail = dropFail;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
