package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ActivityshareConfig {
  /**索引ID*/
  public final int id;

  /**备注*/
  public final String beizhu;

  /**刷新类型*/
  public final int share_type;

  /**兑换奖励*/
  public final int drop;

  /**奖励展示*/
  public final int dropShow;

  /**分享描述*/
  public final String name;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ActivityshareConfig(int id, String beizhu, int share_type, int drop, int dropShow, String name) {
    this.id = id;
    this.beizhu = beizhu;
    this.share_type = share_type;
    this.drop = drop;
    this.dropShow = dropShow;
    this.name = name;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
