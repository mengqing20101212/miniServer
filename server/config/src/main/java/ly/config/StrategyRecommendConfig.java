package ly.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class StrategyRecommendConfig { 
  /**id(这里也当做角色Id)*/ 
   public int id;

  /**推荐阵容（,）*/ 
   public String lineupGroupIds;

  /**关键思路原核（,）*/ 
   public String keySourceIds;

  /**关键思路的描述*/ 
   public String keyThinkingDetail;

  /**推荐原核（,）*/ 
   public String recommendSourceIds;

  /**角色玩法详情描述*/ 
   public String heroPlayDetail;

  /**关键技能1描述（包括技能名称）*/ 
   public String skillDetail;

  /**角色名字*/ 
   public String heroName;

// @@@@@自定义属性开始区@@@@@ 

 // @@@@@自定义属性结束区@@@@@ 

// @@@@@自定义方法开始区@@@@@ 
public void afterLoad() {}


 // @@@@@自定义方法结束区@@@@@ 

 }
