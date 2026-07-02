package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class MainStoryEventConfig {
  /**ID（event唯一id）*/
  public final int id;

  /**名称(备注)*/
  public final String name;

  /**事件类型*/
  public final int type;

  /**参数*/
  public final String values;

  /**事件节点组*/
  public final int group;

  /**场景( 目前每章都是一个场景)*/
  public final String sceneResource;

  /**战斗失败后能否继续往下读取事件*/
  public final int loseContinue;

  /**转场类型*/
  public final int transitionType;

  /**是否预加载(只支持对话并且连续)*/
  public final int isPreload;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public MainStoryEventConfig(int id, String name, int type, String values, int group, String sceneResource, int loseContinue, int transitionType, int isPreload) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.values = values;
    this.group = group;
    this.sceneResource = sceneResource;
    this.loseContinue = loseContinue;
    this.transitionType = transitionType;
    this.isPreload = isPreload;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
