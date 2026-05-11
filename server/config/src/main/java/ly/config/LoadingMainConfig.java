package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class LoadingMainConfig {
  /**id*/
  public final int id;

  /**类型*/
  public final int type;

  /**备注*/
  public final String name;

  /**参数*/
  public final int para;

  /**随机池子*/
  public final String pool;

  /**tips随机池子*/
  public final String tipsPool;

  /**优先读取*/
  public final int isPriority;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public LoadingMainConfig(int id, int type, String name, int para, String pool, String tipsPool, int isPriority) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.para = para;
    this.pool = pool;
    this.tipsPool = tipsPool;
    this.isPriority = isPriority;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
