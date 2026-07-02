package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class DreamEventConfig {
  /**编号*/
  public final int id;

  /**备注*/
  public final String beizhu;

  /**事件类型*/
  public final int type;

  /**立绘id*/
  public final int resourceId;

  /**头像Id*/
  public final int headId;

  /**立绘偏移*/
  public final String shift;

  /**特效id*/
  public final int effectResource;

  /**任务标题*/
  public final String missionTitle;

  /**参数*/
  public final String para;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public DreamEventConfig(int id, String beizhu, int type, int resourceId, int headId, String shift, int effectResource, String missionTitle, String para) {
    this.id = id;
    this.beizhu = beizhu;
    this.type = type;
    this.resourceId = resourceId;
    this.headId = headId;
    this.shift = shift;
    this.effectResource = effectResource;
    this.missionTitle = missionTitle;
    this.para = para;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
