package ly.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class Mail_templateConfig { 
  /**邮件列表中, 邮件图标ID*/ 
   public int icon;

  /**邮件列表中, 描述*/ 
   public String des;

  /**影响分区开始区间，仅福利类邮件有效*/ 
   public int part_begin;

  /**影响分区结束区间，仅福利类邮件有效*/ 
   public int part_end;

  /**邮件模版类型*/ 
   public int type;

  /**预设发送邮件时间,仅福利类邮件用*/ 
   public String stamp;

  /**有效时间秒数, （无论是否阅读，到时间销毁）*/ 
   public int valid_time;

  /**发件人*/ 
   public String sender;

  /**标题*/ 
   public String titile;

  /**内容*/ 
   public String content;

  /**附件*/ 
   public String attach;

// @@@@@自定义属性开始区@@@@@ 

 // @@@@@自定义属性结束区@@@@@ 

// @@@@@自定义方法开始区@@@@@ 
public void afterLoad() {}


 // @@@@@自定义方法结束区@@@@@ 

 }
