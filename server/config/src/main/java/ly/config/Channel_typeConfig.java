package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class Channel_typeConfig {
  /**渠道id*/
  public final int id;

  /**渠道名*/
  public final String spname;

  /**渠道编号*/
  public final int spid;

  /**渠道号*/
  public final String spcode;

  /**更新下载url*/
  public final String spdownloadurl;

  /**审核服ip*/
  public final String checkserverip;

  /**审核服端口*/
  public final int checkserverport;

  /**渠道分组id*/
  public final int groupid;

  /**登录校验测试环境*/
  public final String check_login_test_url;

  /**登录校验正式环境*/
  public final String check_login_url;

  /**登录接口*/
  public final String login_interface;

  /**appid*/
  public final String appid;

  /**sdk服务器秘钥*/
  public final String appsec;

  /**外发渠道*/
  public final int isExteral;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public Channel_typeConfig(int id, String spname, int spid, String spcode, String spdownloadurl, String checkserverip, int checkserverport, int groupid, String check_login_test_url, String check_login_url, String login_interface, String appid, String appsec, int isExteral) {
    this.id = id;
    this.spname = spname;
    this.spid = spid;
    this.spcode = spcode;
    this.spdownloadurl = spdownloadurl;
    this.checkserverip = checkserverip;
    this.checkserverport = checkserverport;
    this.groupid = groupid;
    this.check_login_test_url = check_login_test_url;
    this.check_login_url = check_login_url;
    this.login_interface = login_interface;
    this.appid = appid;
    this.appsec = appsec;
    this.isExteral = isExteral;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
