package ly.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ContactEventConfig { 
  /**编号*/ 
   public int id;

  /**玩法类型*/ 
   public int type;

  /**备注*/ 
   public String beizhu;

  /**玩法参数*/ 
   public int para;

  /**开始对话*/ 
   public String startDialog;

  /**成功对话*/ 
   public String rightDialog;

  /**失败对话*/ 
   public String wrongDialog;

  /**成功奖励*/ 
   public int dropSuccess;

  /**失败奖励*/ 
   public int dropFail;

  /**低保邮件ID(废弃)*/ 
   public int basicMail;

// @@@@@自定义属性开始区@@@@@ 

 // @@@@@自定义属性结束区@@@@@ 

// @@@@@自定义方法开始区@@@@@ 
public void afterLoad() {}


 // @@@@@自定义方法结束区@@@@@ 

 }
