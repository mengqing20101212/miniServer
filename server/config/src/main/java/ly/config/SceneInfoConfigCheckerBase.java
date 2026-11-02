package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class SceneInfoConfigCheckerBase extends AbstractConfigChecker<SceneInfoConfig> {
  @Override
  public String getConfigFileName() {
    return "sceneInfo.xml";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "int"),
        new ConfigColumnMeta(1, "sceneName", "string"),
        new ConfigColumnMeta(2, "sceneResource", "string"),
        new ConfigColumnMeta(3, "levelRequire", "int"),
        new ConfigColumnMeta(4, "sceneRequire", "string"),
        new ConfigColumnMeta(5, "returnStamina", "int"),
        new ConfigColumnMeta(6, "startPerformance", "string"),
        new ConfigColumnMeta(7, "sceneBGM", "int"),
        new ConfigColumnMeta(8, "sceneType", "int"),
        new ConfigColumnMeta(9, "isClient", "int"),
        new ConfigColumnMeta(10, "checkBattle", "int"),
        new ConfigColumnMeta(11, "prepareType", "int"),
        new ConfigColumnMeta(12, "readyWaitTime", "int"),
        new ConfigColumnMeta(13, "activityId", "int"),
        new ConfigColumnMeta(14, "lineupNo", "int"),
        new ConfigColumnMeta(15, "lineupType", "int"),
        new ConfigColumnMeta(16, "appointedNPC", "string"),
        new ConfigColumnMeta(17, "defaultSpEnergy", "int"),
        new ConfigColumnMeta(18, "spEnergyCoe", "int"),
        new ConfigColumnMeta(19, "defaultEnergy", "int"),
        new ConfigColumnMeta(20, "defaultEnergyRecover", "int"),
        new ConfigColumnMeta(21, "defaultEnergyRaise", "int"),
        new ConfigColumnMeta(22, "defaultEnergyRecoverMax", "int"),
        new ConfigColumnMeta(23, "defaultEnergyBar", "int"),
        new ConfigColumnMeta(24, "maxMember", "int"),
        new ConfigColumnMeta(25, "sceneControlResource", "int"),
        new ConfigColumnMeta(26, "bossPos", "string"),
        new ConfigColumnMeta(27, "npcType", "int"),
        new ConfigColumnMeta(28, "sceneNpc_1", "string"),
        new ConfigColumnMeta(29, "sceneNpc_2", "string"),
        new ConfigColumnMeta(30, "sceneNpc_3", "string"),
        new ConfigColumnMeta(31, "sceneNpc_4", "string"),
        new ConfigColumnMeta(32, "sceneNpc_5", "string"),
        new ConfigColumnMeta(33, "battlePosId", "string"),
        new ConfigColumnMeta(34, "knockPosition_a", "string"),
        new ConfigColumnMeta(35, "knockPosition_b", "string"),
        new ConfigColumnMeta(36, "sceneObjects", "string"),
        new ConfigColumnMeta(37, "bonusGroup", "int"),
        new ConfigColumnMeta(38, "fixedBonusGroup", "int"),
        new ConfigColumnMeta(39, "eventIds", "string"),
        new ConfigColumnMeta(40, "changeInfo", "int"),
        new ConfigColumnMeta(41, "endInfo", "string"),
        new ConfigColumnMeta(42, "dropGroup", "int"),
        new ConfigColumnMeta(43, "starType", "int"),
        new ConfigColumnMeta(44, "starList", "string"),
        new ConfigColumnMeta(45, "dropList", "string"),
        new ConfigColumnMeta(46, "exp", "int"),
        new ConfigColumnMeta(47, "gold", "int"),
        new ConfigColumnMeta(48, "firstDrop", "int"),
        new ConfigColumnMeta(49, "summonType", "int"),
        new ConfigColumnMeta(50, "sceneSkills", "string"),
        new ConfigColumnMeta(51, "triggerPro1", "int"),
        new ConfigColumnMeta(52, "triggerStage1", "string"),
        new ConfigColumnMeta(53, "triggerPro2", "int"),
        new ConfigColumnMeta(54, "triggerStage2", "string"),
        new ConfigColumnMeta(55, "light", "int"),
        new ConfigColumnMeta(56, "scenePos", "string"),
        new ConfigColumnMeta(57, "scenePoint", "string"),
        new ConfigColumnMeta(58, "hintType", "string"),
        new ConfigColumnMeta(59, "sceneHint", "string"),
        new ConfigColumnMeta(60, "hintPic", "string"),
        new ConfigColumnMeta(61, "endShow", "bool"),
        new ConfigColumnMeta(62, "isCloseSpeed", "int"),
        new ConfigColumnMeta(63, "isCloseAuto", "bool"),
        new ConfigColumnMeta(64, "isClosePerformance", "bool"),
        new ConfigColumnMeta(65, "isOpenSpecialUi", "int"),
        new ConfigColumnMeta(66, "battleStartUiTpye", "string"),
        new ConfigColumnMeta(67, "showSelfBlood", "int"),
        new ConfigColumnMeta(68, "tryAgain", "int"),
        new ConfigColumnMeta(69, "sceneTimeId", "int"),
        new ConfigColumnMeta(70, "showDetail", "string"),
        new ConfigColumnMeta(71, "changeHeroTime", "int"),
        new ConfigColumnMeta(72, "isShare", "int"),
        new ConfigColumnMeta(73, "strategy", "int"),
        new ConfigColumnMeta(74, "autoSaveLineup", "int"),
        new ConfigColumnMeta(75, "isTryPlay", "int"),
        new ConfigColumnMeta(76, "isChangeSkin", "int"),
        new ConfigColumnMeta(77, "blockBattleConvergeId", "string"));
  }
}
