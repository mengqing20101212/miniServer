package ly.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SceneInfoConfig { 
  /**编号*/ 
   public int id;

  /**场景名称*/ 
   public String sceneName;

  /**场景资源*/ 
   public String sceneResource;

  /**进入等级（作废）*/ 
   public int levelRequire;

  /**前置关卡（作废）*/ 
   public String sceneRequire;

  /**失败体力返还（作废）*/ 
   public int returnStamina;

  /**进场动画*/ 
   public String startPerformance;

  /**场景音乐*/ 
   public int sceneBGM;

  /**关卡类型*/ 
   public int sceneType;

  /**是否只是前端运算*/ 
   public int isClient;

  /**是否验证战斗*/ 
   public int checkBattle;

  /**备战阶段类型*/ 
   public int prepareType;

  /**战前准备时间（ms）*/ 
   public int readyWaitTime;

  /**关联的activityid*/ 
   public int activityId;

  /**默认上阵阵容*/ 
   public int lineupNo;

  /**上阵类型*/ 
   public int lineupType;

  /**指定NPC列表*/ 
   public String appointedNPC;

  /**初始S能量*/ 
   public int defaultSpEnergy;

  /**S能量获取系数*/ 
   public int spEnergyCoe;

  /**初始能量*/ 
   public int defaultEnergy;

  /**能量点初始回复数值*/ 
   public int defaultEnergyRecover;

  /**能量点回复数值增量*/ 
   public int defaultEnergyRaise;

  /**能量点最大回复数值*/ 
   public int defaultEnergyRecoverMax;

  /**初始能量进度*/ 
   public int defaultEnergyBar;

  /**最多上阵数量*/ 
   public int maxMember;

  /**使用站位布点*/ 
   public int sceneControlResource;

  /**BOSS位置信息*/ 
   public String bossPos;

  /**NPC类型*/ 
   public int npcType;

  /**场景NPC列表*/ 
   public String sceneNpc_1;

  /**场景NPC列表*/ 
   public String sceneNpc_2;

  /**场景NPC列表*/ 
   public String sceneNpc_3;

  /**场景NPC列表*/ 
   public String sceneNpc_4;

  /**场景NPC列表*/ 
   public String sceneNpc_5;

  /**角色站位信息*/ 
   public String battlePosId;

  /**击飞位置*/ 
   public String knockPosition_a;

  /**击飞位置*/ 
   public String knockPosition_b;

  /**场景交互物列表*/ 
   public String sceneObjects;

  /**Bonus组*/ 
   public int bonusGroup;

  /**固定Bonus组*/ 
   public int fixedBonusGroup;

  /**战场事件id*/ 
   public String eventIds;

  /**换波处理*/ 
   public int changeInfo;

  /**结束条件*/ 
   public String endInfo;

  /**关卡掉落*/ 
   public int dropGroup;

  /**关卡评分类型*/ 
   public int starType;

  /**关卡评分list*/ 
   public String starList;

  /**关卡掉落list*/ 
   public String dropList;

  /**关卡经验*/ 
   public int exp;

  /**关卡金币*/ 
   public int gold;

  /**首通掉落*/ 
   public int firstDrop;

  /**埼玉召唤类型*/ 
   public int summonType;

  /**场景技能*/ 
   public String sceneSkills;

  /**限时挑战触发概率*/ 
   public int triggerPro1;

  /**限时挑战触发事件*/ 
   public String triggerStage1;

  /**大体力玩法触发概率*/ 
   public int triggerPro2;

  /**限时挑战触发事件*/ 
   public String triggerStage2;

  /**光照方向*/ 
   public int light;

  /**场景偏移*/ 
   public String scenePos;

  /**关卡提示tips*/ 
   public String scenePoint;

  /**关卡机制提示类型*/ 
   public String hintType;

  /**关卡机制提示*/ 
   public String sceneHint;

  /**关卡提示图片*/ 
   public String hintPic;

  /**是否有关卡结算展示*/ 
   public String endShow;

  /**是否强制N倍速*/ 
   public int isCloseSpeed;

  /**是否强制手动战斗*/ 
   public String isCloseAuto;

  /**是否强制开启演出*/ 
   public String isClosePerformance;

  /**是否开启特殊UI边缘*/ 
   public int isOpenSpecialUi;

  /**开始战斗的特殊UI展示类型（波次,UI类型|波次,UI类型）*/ 
   public String battleStartUiTpye;

  /**是否显示我方血条*/ 
   public int showSelfBlood;

  /**失败是否再次挑战*/ 
   public int tryAgain;

  /**等待时间管理id*/ 
   public int sceneTimeId;

  /**是否显示战斗详情*/ 
   public String showDetail;

  /**战前换阵容时间限制（单位ms）*/ 
   public int changeHeroTime;

  /**时间是否共用*/ 
   public int isShare;

  /**攻略ID*/ 
   public int strategy;

  /**是否自动保存阵容*/ 
   public int autoSaveLineup;

  /**是否试玩关卡*/ 
   public int isTryPlay;

  /**是否试玩关卡*/ 
   public int isChangeSkin;

  /**托管默认集火目标*/ 
   public String blockBattleConvergeId;

// @@@@@自定义属性开始区@@@@@ 

 // @@@@@自定义属性结束区@@@@@ 

// @@@@@自定义方法开始区@@@@@ 
public void afterLoad() {}


 // @@@@@自定义方法结束区@@@@@ 

 }
