package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GuildLabelConfig {
  /**编号*/
  public final int id;

  /**标签类型*/
  public final int type;

  /**显示图片背景（废弃）*/
  public final int degree;

  /**标签内容*/
  public final String content;

  /**颜色*/
  public final String color;

  /**颜色*/
  public final String color2;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public GuildLabelConfig(int id, int type, int degree, String content, String color, String color2) {
    this.id = id;
    this.type = type;
    this.degree = degree;
    this.content = content;
    this.color = color;
    this.color2 = color2;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
