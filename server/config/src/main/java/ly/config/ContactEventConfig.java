package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ContactEventConfig {
  /**编号*/
  public final int id;

  /**玩法类型*/
  public final int type;

  /**备注*/
  public final String beizhu;

  /**玩法参数*/
  public final int para;

  /**开始对话*/
  public final String startDialog;

  /**成功对话*/
  public final String rightDialog;

  /**失败对话*/
  public final String wrongDialog;

  /**成功奖励*/
  public final int dropSuccess;

  /**失败奖励*/
  public final int dropFail;

  /**低保邮件ID(废弃)*/
  public final int basicMail;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public ContactEventConfig(int id, int type, String beizhu, int para, String startDialog, String rightDialog, String wrongDialog, int dropSuccess, int dropFail, int basicMail) {
    this.id = id;
    this.type = type;
    this.beizhu = beizhu;
    this.para = para;
    this.startDialog = startDialog;
    this.rightDialog = rightDialog;
    this.wrongDialog = wrongDialog;
    this.dropSuccess = dropSuccess;
    this.dropFail = dropFail;
    this.basicMail = basicMail;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
