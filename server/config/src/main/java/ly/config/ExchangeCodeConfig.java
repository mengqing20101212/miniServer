package ly.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ExchangeCodeConfig { 
  /**自增id*/ 
   public int groupId;

  /**礼包名称*/ 
   public String name;

  /**开始领取时间*/ 
   public String beginTime;

  /**结束领取时间*/ 
   public String endTime;

  /**生成兑换码个数*/ 
   public int codeNum;

  /**渠道id*/ 
   public String channel;

  /**获取的奖励列表*/ 
   public String rewards;

  /**一个礼包码可有多少个角色激活，-1表示不限*/ 
   public int limit1;

  /**一个角色可以激活同一批礼包码数量*/ 
   public int limit2;

  /**该批礼包码能够被那些服务器使用，-1为所有*/ 
   public String limit3;

// @@@@@自定义属性开始区@@@@@ 

 // @@@@@自定义属性结束区@@@@@ 

// @@@@@自定义方法开始区@@@@@ 
public void afterLoad() {}


 // @@@@@自定义方法结束区@@@@@ 

 }
