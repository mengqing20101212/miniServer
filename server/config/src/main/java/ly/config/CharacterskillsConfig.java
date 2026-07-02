package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class CharacterskillsConfig {
  /**战斗模型*/
  public final int id;

  /**编号*/
  public final int heroid;

  /**备注名*/
  public final String config_name;

  /**人物立绘资源*/
  public final int headResource_S;

  /**背景资源*/
  public final int bgResource;

  /**背景线性资源*/
  public final int bgResource_0;

  /**技能名称资源*/
  public final int skillResource;

  /**特效资源1*/
  public final int effectResource_1;

  /**特效资源2*/
  public final int effectResource_2;

  /**颜色*/
  public final String color;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public CharacterskillsConfig(int id, int heroid, String config_name, int headResource_S, int bgResource, int bgResource_0, int skillResource, int effectResource_1, int effectResource_2, String color) {
    this.id = id;
    this.heroid = heroid;
    this.config_name = config_name;
    this.headResource_S = headResource_S;
    this.bgResource = bgResource;
    this.bgResource_0 = bgResource_0;
    this.skillResource = skillResource;
    this.effectResource_1 = effectResource_1;
    this.effectResource_2 = effectResource_2;
    this.color = color;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
