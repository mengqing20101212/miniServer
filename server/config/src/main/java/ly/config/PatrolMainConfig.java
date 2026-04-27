package ly.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PatrolMainConfig { 
  /**id*/ 
   public int id;

  /**所属组别*/ 
   public int groupId;

  /**消耗体力*/ 
   public int cost;

  /**任务刷新权重*/ 
   public int weight;

  /**任务名称*/ 
   public String name;

  /**图标*/ 
   public int icon;

  /**道具奖励*/ 
   public int dropGroupId;

  /**道具预览*/ 
   public String itemPre;

  /**人数要求*/ 
   public int heroNumLimit;

  /**时间要求（秒？）*/ 
   public int timeConsume;

  /**特殊奖励*/ 
   public int extraDropGroupId;

  /**特殊奖励初识触发几率*/ 
   public int extraDropPro;

  /**等级出现范围*/ 
   public String lvLimit;

  /**任务描述*/ 
   public String desc;

  /**开始事件*/ 
   public int startEvent;

  /**触发事件数量*/ 
   public String eventNum;

  /**触发时间随机范围（min）*/ 
   public String eventTime;

  /**事件概率*/ 
   public String eventPro;

// @@@@@自定义属性开始区@@@@@ 

 // @@@@@自定义属性结束区@@@@@ 

// @@@@@自定义方法开始区@@@@@ 
public void afterLoad() {}


 // @@@@@自定义方法结束区@@@@@ 

 }
