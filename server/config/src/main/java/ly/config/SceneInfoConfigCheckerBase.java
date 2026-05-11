package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SceneInfoConfigCheckerBase extends AbstractConfigChecker<SceneInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "sceneInfo.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "sceneName", "STRING"),
        new ConfigColumnMeta(2, "sceneResource", "STRING"),
        new ConfigColumnMeta(3, "levelRequire", "INT"),
        new ConfigColumnMeta(4, "sceneRequire", "STRING"),
        new ConfigColumnMeta(5, "returnStamina", "INT"),
        new ConfigColumnMeta(6, "startPerformance", "STRING"),
        new ConfigColumnMeta(7, "sceneBGM", "INT"),
        new ConfigColumnMeta(8, "sceneType", "INT"),
        new ConfigColumnMeta(9, "isClient", "INT"),
        new ConfigColumnMeta(10, "checkBattle", "INT"),
        new ConfigColumnMeta(11, "prepareType", "INT"),
        new ConfigColumnMeta(12, "readyWaitTime", "INT"),
        new ConfigColumnMeta(13, "activityId", "INT"),
        new ConfigColumnMeta(14, "lineupNo", "INT"),
        new ConfigColumnMeta(15, "lineupType", "INT"),
        new ConfigColumnMeta(16, "appointedNPC", "STRING"),
        new ConfigColumnMeta(17, "defaultSpEnergy", "INT"),
        new ConfigColumnMeta(18, "spEnergyCoe", "INT"),
        new ConfigColumnMeta(19, "defaultEnergy", "INT"),
        new ConfigColumnMeta(20, "defaultEnergyRecover", "INT"),
        new ConfigColumnMeta(21, "defaultEnergyRaise", "INT"),
        new ConfigColumnMeta(22, "defaultEnergyRecoverMax", "INT"),
        new ConfigColumnMeta(23, "defaultEnergyBar", "INT"),
        new ConfigColumnMeta(24, "maxMember", "INT"),
        new ConfigColumnMeta(25, "sceneControlResource", "INT"),
        new ConfigColumnMeta(26, "bossPos", "STRING"),
        new ConfigColumnMeta(27, "npcType", "INT"),
        new ConfigColumnMeta(28, "sceneNpc_1", "STRING"),
        new ConfigColumnMeta(29, "sceneNpc_2", "STRING"),
        new ConfigColumnMeta(30, "sceneNpc_3", "STRING"),
        new ConfigColumnMeta(31, "sceneNpc_4", "STRING"),
        new ConfigColumnMeta(32, "sceneNpc_5", "STRING"),
        new ConfigColumnMeta(33, "battlePosId", "STRING"),
        new ConfigColumnMeta(34, "knockPosition_a", "STRING"),
        new ConfigColumnMeta(35, "knockPosition_b", "STRING"),
        new ConfigColumnMeta(36, "sceneObjects", "STRING"),
        new ConfigColumnMeta(37, "bonusGroup", "INT"),
        new ConfigColumnMeta(38, "fixedBonusGroup", "INT"),
        new ConfigColumnMeta(39, "eventIds", "STRING"),
        new ConfigColumnMeta(40, "changeInfo", "INT"),
        new ConfigColumnMeta(41, "endInfo", "STRING"),
        new ConfigColumnMeta(42, "dropGroup", "INT"),
        new ConfigColumnMeta(43, "starType", "INT"),
        new ConfigColumnMeta(44, "starList", "STRING"),
        new ConfigColumnMeta(45, "dropList", "STRING"),
        new ConfigColumnMeta(46, "exp", "INT"),
        new ConfigColumnMeta(47, "gold", "INT"),
        new ConfigColumnMeta(48, "firstDrop", "INT"),
        new ConfigColumnMeta(49, "summonType", "INT"),
        new ConfigColumnMeta(50, "sceneSkills", "STRING"),
        new ConfigColumnMeta(51, "triggerPro1", "INT"),
        new ConfigColumnMeta(52, "triggerStage1", "STRING"),
        new ConfigColumnMeta(53, "triggerPro2", "INT"),
        new ConfigColumnMeta(54, "triggerStage2", "STRING"),
        new ConfigColumnMeta(55, "light", "INT"),
        new ConfigColumnMeta(56, "scenePos", "STRING"),
        new ConfigColumnMeta(57, "scenePoint", "LIST"),
        new ConfigColumnMeta(58, "hintType", "STRING"),
        new ConfigColumnMeta(59, "sceneHint", "STRING"),
        new ConfigColumnMeta(60, "hintPic", "STRING"),
        new ConfigColumnMeta(61, "endShow", "BOOL"),
        new ConfigColumnMeta(62, "isCloseSpeed", "INT"),
        new ConfigColumnMeta(63, "isCloseAuto", "BOOL"),
        new ConfigColumnMeta(64, "isClosePerformance", "BOOL"),
        new ConfigColumnMeta(65, "isOpenSpecialUi", "INT"),
        new ConfigColumnMeta(66, "battleStartUiTpye", "STRING"),
        new ConfigColumnMeta(67, "showSelfBlood", "INT"),
        new ConfigColumnMeta(68, "tryAgain", "INT"),
        new ConfigColumnMeta(69, "sceneTimeId", "INT"),
        new ConfigColumnMeta(70, "showDetail", "STRING"),
        new ConfigColumnMeta(71, "changeHeroTime", "INT"),
        new ConfigColumnMeta(72, "isShare", "INT"),
        new ConfigColumnMeta(73, "strategy", "INT"),
        new ConfigColumnMeta(74, "autoSaveLineup", "INT"),
        new ConfigColumnMeta(75, "isTryPlay", "INT"),
        new ConfigColumnMeta(76, "isChangeSkin", "INT"),
        new ConfigColumnMeta(77, "blockBattleConvergeId", "STRING"));
  }
}
