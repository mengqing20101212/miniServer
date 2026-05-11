package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DungeonDescribeConfig {
  /**编号*/
  public final int id;

  /**标题*/
  public final String title;

  /**内容*/
  public final String content;

  /**资源id*/
  public final int res;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DungeonDescribeConfig(int id, String title, String content, int res) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.res = res;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
