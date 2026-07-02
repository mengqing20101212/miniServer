package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class Resource_index1Config {
  /**资源id*/
  public final int id;

  /**资源名*/
  public final String resname;

  /**资源路径*/
  public final String respath;

  /**资源类型*/
  public final int language;

  /**资源类型*/
  public final String restype;

  /**是否常驻内存*/
  public final int resident;

  /**描述*/
  public final String des;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public Resource_index1Config(int id, String resname, String respath, int language, String restype, int resident, String des) {
    this.id = id;
    this.resname = resname;
    this.respath = respath;
    this.language = language;
    this.restype = restype;
    this.resident = resident;
    this.des = des;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
