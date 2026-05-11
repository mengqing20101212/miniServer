package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class Mail_templateConfig {
  /**邮件列表中, 邮件图标ID*/
  public final int icon;

  /**邮件列表中, 描述*/
  public final String des;

  /**影响分区开始区间，仅福利类邮件有效*/
  public final int part_begin;

  /**影响分区结束区间，仅福利类邮件有效*/
  public final int part_end;

  /**邮件模版类型*/
  public final int type;

  /**预设发送邮件时间,仅福利类邮件用*/
  public final String stamp;

  /**有效时间秒数, （无论是否阅读，到时间销毁）*/
  public final int valid_time;

  /**发件人*/
  public final String sender;

  /**标题*/
  public final String titile;

  /**内容*/
  public final String content;

  /**附件*/
  public final String attach;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public Mail_templateConfig(int icon, String des, int part_begin, int part_end, int type, String stamp, int valid_time, String sender, String titile, String content, String attach) {
    this.icon = icon;
    this.des = des;
    this.part_begin = part_begin;
    this.part_end = part_end;
    this.type = type;
    this.stamp = stamp;
    this.valid_time = valid_time;
    this.sender = sender;
    this.titile = titile;
    this.content = content;
    this.attach = attach;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
