package ly.config;

import java.util.List;
import ly.utils.KV;

/***
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class SceneInfoConfig {
  public final int id;

  public final String sceneName;

  public final String sceneResource;

  public final int levelRequire;

  public final String sceneRequire;

  public final int returnStamina;

  public final String startPerformance;

  public final int sceneBGM;

  public final int sceneType;

  public final int isClient;

  public final int checkBattle;

  public final int prepareType;

  public final int readyWaitTime;

  public final int activityId;

  public final int lineupNo;

  public final int lineupType;

  public final String appointedNPC;

  public final int defaultSpEnergy;

  public final int spEnergyCoe;

  public final int defaultEnergy;

  public final int defaultEnergyRecover;

  public final int defaultEnergyRaise;

  public final int defaultEnergyRecoverMax;

  public final int defaultEnergyBar;

  public final int maxMember;

  public final int sceneControlResource;

  public final String bossPos;

  public final int npcType;

  public final String sceneNpc_1;

  public final String sceneNpc_2;

  public final String sceneNpc_3;

  public final String sceneNpc_4;

  public final String sceneNpc_5;

  public final String battlePosId;

  public final String knockPosition_a;

  public final String knockPosition_b;

  public final String sceneObjects;

  public final int bonusGroup;

  public final int fixedBonusGroup;

  public final String eventIds;

  public final int changeInfo;

  public final String endInfo;

  public final int dropGroup;

  public final int starType;

  public final String starList;

  public final String dropList;

  public final int exp;

  public final int gold;

  public final int firstDrop;

  public final int summonType;

  public final String sceneSkills;

  public final int triggerPro1;

  public final String triggerStage1;

  public final int triggerPro2;

  public final String triggerStage2;

  public final int light;

  public final String scenePos;

  public final String scenePoint;

  public final String hintType;

  public final String sceneHint;

  public final String hintPic;

  public final boolean endShow;

  public final int isCloseSpeed;

  public final boolean isCloseAuto;

  public final boolean isClosePerformance;

  public final int isOpenSpecialUi;

  public final String battleStartUiTpye;

  public final int showSelfBlood;

  public final int tryAgain;

  public final int sceneTimeId;

  public final String showDetail;

  public final int changeHeroTime;

  public final int isShare;

  public final int strategy;

  public final int autoSaveLineup;

  public final int isTryPlay;

  public final int isChangeSkin;

  public final String blockBattleConvergeId;

  // @@@@@自定义属性开始区@@@@@

  // @@@@@自定义属性结束区@@@@@

  public SceneInfoConfig(int id, String sceneName, String sceneResource, int levelRequire, String sceneRequire, int returnStamina, String startPerformance, int sceneBGM, int sceneType, int isClient, int checkBattle, int prepareType, int readyWaitTime, int activityId, int lineupNo, int lineupType, String appointedNPC, int defaultSpEnergy, int spEnergyCoe, int defaultEnergy, int defaultEnergyRecover, int defaultEnergyRaise, int defaultEnergyRecoverMax, int defaultEnergyBar, int maxMember, int sceneControlResource, String bossPos, int npcType, String sceneNpc_1, String sceneNpc_2, String sceneNpc_3, String sceneNpc_4, String sceneNpc_5, String battlePosId, String knockPosition_a, String knockPosition_b, String sceneObjects, int bonusGroup, int fixedBonusGroup, String eventIds, int changeInfo, String endInfo, int dropGroup, int starType, String starList, String dropList, int exp, int gold, int firstDrop, int summonType, String sceneSkills, int triggerPro1, String triggerStage1, int triggerPro2, String triggerStage2, int light, String scenePos, String scenePoint, String hintType, String sceneHint, String hintPic, boolean endShow, int isCloseSpeed, boolean isCloseAuto, boolean isClosePerformance, int isOpenSpecialUi, String battleStartUiTpye, int showSelfBlood, int tryAgain, int sceneTimeId, String showDetail, int changeHeroTime, int isShare, int strategy, int autoSaveLineup, int isTryPlay, int isChangeSkin, String blockBattleConvergeId) {
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
