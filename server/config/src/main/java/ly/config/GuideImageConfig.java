package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuideImageConfig {
  /**编号*/
  public final int id;

  /**备注*/
  public final String beizhu;

  /**标题*/
  public final String title;

  /**资源id的List*/
  public final String resourceList;

  /**是否检查下一步引导*/
  public final int triggerNext;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuideImageConfig(int id, String beizhu, String title, String resourceList, int triggerNext) {
    this.id = id;
    this.beizhu = beizhu;
    this.title = title;
    this.resourceList = resourceList;
    this.triggerNext = triggerNext;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
