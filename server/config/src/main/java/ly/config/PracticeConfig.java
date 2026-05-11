package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PracticeConfig {
  /**ID*/
  public final int id;

  /**组*/
  public final int group;

  /**名称*/
  public final String name;

  /**奖励掉落id*/
  public final int dropId;

  /**奖励预览*/
  public final String dropShow;

  /**实际关卡id*/
  public final int sceneId;

  /**下一关*/
  public final int nextId;

  /**上一关*/
  public final int lastId;

  /**关卡描述*/
  public final String describe;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public PracticeConfig(int id, int group, String name, int dropId, String dropShow, int sceneId, int nextId, int lastId, String describe) {
    this.id = id;
    this.group = group;
    this.name = name;
    this.dropId = dropId;
    this.dropShow = dropShow;
    this.sceneId = sceneId;
    this.nextId = nextId;
    this.lastId = lastId;
    this.describe = describe;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
