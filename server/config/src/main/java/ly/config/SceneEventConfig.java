package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SceneEventConfig {
  /**事件编号*/
  public final int id;

  /**事件描述*/
  public final String description;

  /**触发类型列表*/
  public final String triggerTypeList;

  /**使用行为树名称*/
  public final String treeName;

  /**事件UI类型*/
  public final int uitype;

  /**参数1*/
  public final String param_1;

  /**参数2*/
  public final String param_2;

  /**参数3*/
  public final String param_3;

  /**参数4*/
  public final String param_4;

  /**循环次数*/
  public final int loopNum;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SceneEventConfig(int id, String description, String triggerTypeList, String treeName, int uitype, String param_1, String param_2, String param_3, String param_4, int loopNum) {
    this.id = id;
    this.description = description;
    this.triggerTypeList = triggerTypeList;
    this.treeName = treeName;
    this.uitype = uitype;
    this.param_1 = param_1;
    this.param_2 = param_2;
    this.param_3 = param_3;
    this.param_4 = param_4;
    this.loopNum = loopNum;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
