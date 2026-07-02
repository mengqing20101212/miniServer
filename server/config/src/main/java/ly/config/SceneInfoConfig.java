package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SceneInfoConfig {
  /**编号*/
  public final int id;

  /**场景名称*/
  public final String sceneName;

  /**场景资源*/
  public final String sceneResource;

  /**进入等级（作废）*/
  public final int levelRequire;

  /**前置关卡（作废）*/
  public final String sceneRequire;

  /**失败体力返还（作废）*/
  public final int returnStamina;

  /**进场动画*/
  public final String startPerformance;

  /**场景音乐*/
  public final int sceneBGM;

  /**关卡类型*/
  public final int sceneType;

  /**是否只是前端运算*/
  public final int isClient;

  /**是否验证战斗*/
  public final int checkBattle;

  /**备战阶段类型*/
  public final int prepareType;

  /**战前准备时间（ms）*/
  public final int readyWaitTime;

  /**关联的activityid*/
  public final int activityId;

  /**默认上阵阵容*/
  public final int lineupNo;

  /**上阵类型*/
  public final int lineupType;

  /**指定NPC列表*/
  public final String appointedNPC;

  /**初始S能量*/
  public final int defaultSpEnergy;

  /**S能量获取系数*/
  public final int spEnergyCoe;

  /**初始能量*/
  public final int defaultEnergy;

  /**能量点初始回复数值*/
  public final int defaultEnergyRecover;

  /**能量点回复数值增量*/
  public final int defaultEnergyRaise;

  /**能量点最大回复数值*/
  public final int defaultEnergyRecoverMax;

  /**初始能量进度*/
  public final int defaultEnergyBar;

  /**最多上阵数量*/
  public final int maxMember;

  /**使用站位布点*/
  public final int sceneControlResource;

  /**BOSS位置信息*/
  public final String bossPos;

  /**NPC类型*/
  public final int npcType;

  /**场景NPC列表*/
  public final String sceneNpc_1;

  /**场景NPC列表*/
  public final String sceneNpc_2;

  /**场景NPC列表*/
  public final String sceneNpc_3;

  /**场景NPC列表*/
  public final String sceneNpc_4;

  /**场景NPC列表*/
  public final String sceneNpc_5;

  /**角色站位信息*/
  public final String battlePosId;

  /**击飞位置*/
  public final String knockPosition_a;

  /**击飞位置*/
  public final String knockPosition_b;

  /**场景交互物列表*/
  public final String sceneObjects;

  /**Bonus组*/
  public final int bonusGroup;

  /**固定Bonus组*/
  public final int fixedBonusGroup;

  /**战场事件id*/
  public final String eventIds;

  /**换波处理*/
  public final int changeInfo;

  /**结束条件*/
  public final String endInfo;

  /**关卡掉落*/
  public final int dropGroup;

  /**关卡评分类型*/
  public final int starType;

  /**关卡评分list*/
  public final String starList;

  /**关卡掉落list*/
  public final String dropList;

  /**关卡经验*/
  public final int exp;

  /**关卡金币*/
  public final int gold;

  /**首通掉落*/
  public final int firstDrop;

  /**埼玉召唤类型*/
  public final int summonType;

  /**场景技能*/
  public final String sceneSkills;

  /**限时挑战触发概率*/
  public final int triggerPro1;

  /**限时挑战触发事件*/
  public final String triggerStage1;

  /**大体力玩法触发概率*/
  public final int triggerPro2;

  /**限时挑战触发事件*/
  public final String triggerStage2;

  /**光照方向*/
  public final int light;

  /**场景偏移*/
  public final String scenePos;

  /**关卡提示tips*/
  public final String scenePoint;

  /**关卡机制提示类型*/
  public final String hintType;

  /**关卡机制提示*/
  public final String sceneHint;

  /**关卡提示图片*/
  public final String hintPic;

  /**是否有关卡结算展示*/
  public final String endShow;

  /**是否强制N倍速*/
  public final int isCloseSpeed;

  /**是否强制手动战斗*/
  public final String isCloseAuto;

  /**是否强制开启演出*/
  public final String isClosePerformance;

  /**是否开启特殊UI边缘*/
  public final int isOpenSpecialUi;

  /**开始战斗的特殊UI展示类型（波次,UI类型|波次,UI类型）*/
  public final String battleStartUiTpye;

  /**是否显示我方血条*/
  public final int showSelfBlood;

  /**失败是否再次挑战*/
  public final int tryAgain;

  /**等待时间管理id*/
  public final int sceneTimeId;

  /**是否显示战斗详情*/
  public final String showDetail;

  /**战前换阵容时间限制（单位ms）*/
  public final int changeHeroTime;

  /**时间是否共用*/
  public final int isShare;

  /**攻略ID*/
  public final int strategy;

  /**是否自动保存阵容*/
  public final int autoSaveLineup;

  /**是否试玩关卡*/
  public final int isTryPlay;

  /**是否试玩关卡*/
  public final int isChangeSkin;

  /**托管默认集火目标*/
  public final String blockBattleConvergeId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SceneInfoConfig(int id, String sceneName, String sceneResource, int levelRequire, String sceneRequire, int returnStamina, String startPerformance, int sceneBGM, int sceneType, int isClient, int checkBattle, int prepareType, int readyWaitTime, int activityId, int lineupNo, int lineupType, String appointedNPC, int defaultSpEnergy, int spEnergyCoe, int defaultEnergy, int defaultEnergyRecover, int defaultEnergyRaise, int defaultEnergyRecoverMax, int defaultEnergyBar, int maxMember, int sceneControlResource, String bossPos, int npcType, String sceneNpc_1, String sceneNpc_2, String sceneNpc_3, String sceneNpc_4, String sceneNpc_5, String battlePosId, String knockPosition_a, String knockPosition_b, String sceneObjects, int bonusGroup, int fixedBonusGroup, String eventIds, int changeInfo, String endInfo, int dropGroup, int starType, String starList, String dropList, int exp, int gold, int firstDrop, int summonType, String sceneSkills, int triggerPro1, String triggerStage1, int triggerPro2, String triggerStage2, int light, String scenePos, String scenePoint, String hintType, String sceneHint, String hintPic, String endShow, int isCloseSpeed, String isCloseAuto, String isClosePerformance, int isOpenSpecialUi, String battleStartUiTpye, int showSelfBlood, int tryAgain, int sceneTimeId, String showDetail, int changeHeroTime, int isShare, int strategy, int autoSaveLineup, int isTryPlay, int isChangeSkin, String blockBattleConvergeId) {
    this.id = id;
    this.sceneName = sceneName;
    this.sceneResource = sceneResource;
    this.levelRequire = levelRequire;
    this.sceneRequire = sceneRequire;
    this.returnStamina = returnStamina;
    this.startPerformance = startPerformance;
    this.sceneBGM = sceneBGM;
    this.sceneType = sceneType;
    this.isClient = isClient;
    this.checkBattle = checkBattle;
    this.prepareType = prepareType;
    this.readyWaitTime = readyWaitTime;
    this.activityId = activityId;
    this.lineupNo = lineupNo;
    this.lineupType = lineupType;
    this.appointedNPC = appointedNPC;
    this.defaultSpEnergy = defaultSpEnergy;
    this.spEnergyCoe = spEnergyCoe;
    this.defaultEnergy = defaultEnergy;
    this.defaultEnergyRecover = defaultEnergyRecover;
    this.defaultEnergyRaise = defaultEnergyRaise;
    this.defaultEnergyRecoverMax = defaultEnergyRecoverMax;
    this.defaultEnergyBar = defaultEnergyBar;
    this.maxMember = maxMember;
    this.sceneControlResource = sceneControlResource;
    this.bossPos = bossPos;
    this.npcType = npcType;
    this.sceneNpc_1 = sceneNpc_1;
    this.sceneNpc_2 = sceneNpc_2;
    this.sceneNpc_3 = sceneNpc_3;
    this.sceneNpc_4 = sceneNpc_4;
    this.sceneNpc_5 = sceneNpc_5;
    this.battlePosId = battlePosId;
    this.knockPosition_a = knockPosition_a;
    this.knockPosition_b = knockPosition_b;
    this.sceneObjects = sceneObjects;
    this.bonusGroup = bonusGroup;
    this.fixedBonusGroup = fixedBonusGroup;
    this.eventIds = eventIds;
    this.changeInfo = changeInfo;
    this.endInfo = endInfo;
    this.dropGroup = dropGroup;
    this.starType = starType;
    this.starList = starList;
    this.dropList = dropList;
    this.exp = exp;
    this.gold = gold;
    this.firstDrop = firstDrop;
    this.summonType = summonType;
    this.sceneSkills = sceneSkills;
    this.triggerPro1 = triggerPro1;
    this.triggerStage1 = triggerStage1;
    this.triggerPro2 = triggerPro2;
    this.triggerStage2 = triggerStage2;
    this.light = light;
    this.scenePos = scenePos;
    this.scenePoint = scenePoint;
    this.hintType = hintType;
    this.sceneHint = sceneHint;
    this.hintPic = hintPic;
    this.endShow = endShow;
    this.isCloseSpeed = isCloseSpeed;
    this.isCloseAuto = isCloseAuto;
    this.isClosePerformance = isClosePerformance;
    this.isOpenSpecialUi = isOpenSpecialUi;
    this.battleStartUiTpye = battleStartUiTpye;
    this.showSelfBlood = showSelfBlood;
    this.tryAgain = tryAgain;
    this.sceneTimeId = sceneTimeId;
    this.showDetail = showDetail;
    this.changeHeroTime = changeHeroTime;
    this.isShare = isShare;
    this.strategy = strategy;
    this.autoSaveLineup = autoSaveLineup;
    this.isTryPlay = isTryPlay;
    this.isChangeSkin = isChangeSkin;
    this.blockBattleConvergeId = blockBattleConvergeId;
  }

  // @@@@@自定义方法开始区@@@@@
public void afterLoad() {}
  // @@@@@自定义方法结束区@@@@@
}
