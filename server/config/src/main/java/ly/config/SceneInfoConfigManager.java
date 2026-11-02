package ly.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ly.AbstractConfigManger;
import ly.ConfigLoadException;
import ly.InterfaceConfigManagerProxy;
import ly.utils.KV;
import org.slf4j.Logger;

/*
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 * File: SceneInfoConfigManager
 */
public class SceneInfoConfigManager implements InterfaceConfigManagerProxy {
  public static SceneInfoConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SceneInfoConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SceneInfoConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "sceneInfo.xml"; }

  public static class SceneInfoConfigManagerImpl extends AbstractConfigManger {
    private List<SceneInfoConfig> configList = List.of();
    private Map<Integer, SceneInfoConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type24 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type25 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type26 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type27 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type28 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type29 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type30 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type31 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type32 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type33 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type34 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type35 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type36 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type37 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type38 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type39 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type40 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type41 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type42 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type43 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type44 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type45 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type46 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type47 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type48 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type49 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type50 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type51 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type52 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type53 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type54 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type55 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type56 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type57 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type58 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type59 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type60 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type61 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type62 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type63 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type64 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type65 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type66 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type67 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type68 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type69 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type70 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type71 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type72 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type73 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type74 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type75 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type76 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type77 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      SceneInfoConfigChecker checker = new SceneInfoConfigChecker();
      List<SceneInfoConfig> newList = new ArrayList<>();
      Map<Integer, SceneInfoConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          int parsedField3 = 0;
          String parsedField4 = null;
          int parsedField5 = 0;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          int parsedField15 = 0;
          String parsedField16 = null;
          int parsedField17 = 0;
          int parsedField18 = 0;
          int parsedField19 = 0;
          int parsedField20 = 0;
          int parsedField21 = 0;
          int parsedField22 = 0;
          int parsedField23 = 0;
          int parsedField24 = 0;
          int parsedField25 = 0;
          String parsedField26 = null;
          int parsedField27 = 0;
          String parsedField28 = null;
          String parsedField29 = null;
          String parsedField30 = null;
          String parsedField31 = null;
          String parsedField32 = null;
          String parsedField33 = null;
          String parsedField34 = null;
          String parsedField35 = null;
          String parsedField36 = null;
          int parsedField37 = 0;
          int parsedField38 = 0;
          String parsedField39 = null;
          int parsedField40 = 0;
          String parsedField41 = null;
          int parsedField42 = 0;
          int parsedField43 = 0;
          String parsedField44 = null;
          String parsedField45 = null;
          int parsedField46 = 0;
          int parsedField47 = 0;
          int parsedField48 = 0;
          int parsedField49 = 0;
          String parsedField50 = null;
          int parsedField51 = 0;
          String parsedField52 = null;
          int parsedField53 = 0;
          String parsedField54 = null;
          int parsedField55 = 0;
          String parsedField56 = null;
          String parsedField57 = null;
          String parsedField58 = null;
          String parsedField59 = null;
          String parsedField60 = null;
          boolean parsedField61 = false;
          int parsedField62 = 0;
          boolean parsedField63 = false;
          boolean parsedField64 = false;
          int parsedField65 = 0;
          String parsedField66 = null;
          int parsedField67 = 0;
          int parsedField68 = 0;
          int parsedField69 = 0;
          String parsedField70 = null;
          int parsedField71 = 0;
          int parsedField72 = 0;
          int parsedField73 = 0;
          int parsedField74 = 0;
          int parsedField75 = 0;
          int parsedField76 = 0;
          String parsedField77 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("sceneName"), fileName + ":行" + lineNumber + ":字段 sceneName");
            parsedField2 = ly.ConfigValues.read(type2, values.get("sceneResource"), fileName + ":行" + lineNumber + ":字段 sceneResource");
            parsedField3 = ly.ConfigValues.read(type3, values.get("levelRequire"), fileName + ":行" + lineNumber + ":字段 levelRequire");
            parsedField4 = ly.ConfigValues.read(type4, values.get("sceneRequire"), fileName + ":行" + lineNumber + ":字段 sceneRequire");
            parsedField5 = ly.ConfigValues.read(type5, values.get("returnStamina"), fileName + ":行" + lineNumber + ":字段 returnStamina");
            parsedField6 = ly.ConfigValues.read(type6, values.get("startPerformance"), fileName + ":行" + lineNumber + ":字段 startPerformance");
            parsedField7 = ly.ConfigValues.read(type7, values.get("sceneBGM"), fileName + ":行" + lineNumber + ":字段 sceneBGM");
            parsedField8 = ly.ConfigValues.read(type8, values.get("sceneType"), fileName + ":行" + lineNumber + ":字段 sceneType");
            parsedField9 = ly.ConfigValues.read(type9, values.get("isClient"), fileName + ":行" + lineNumber + ":字段 isClient");
            parsedField10 = ly.ConfigValues.read(type10, values.get("checkBattle"), fileName + ":行" + lineNumber + ":字段 checkBattle");
            parsedField11 = ly.ConfigValues.read(type11, values.get("prepareType"), fileName + ":行" + lineNumber + ":字段 prepareType");
            parsedField12 = ly.ConfigValues.read(type12, values.get("readyWaitTime"), fileName + ":行" + lineNumber + ":字段 readyWaitTime");
            parsedField13 = ly.ConfigValues.read(type13, values.get("activityId"), fileName + ":行" + lineNumber + ":字段 activityId");
            parsedField14 = ly.ConfigValues.read(type14, values.get("lineupNo"), fileName + ":行" + lineNumber + ":字段 lineupNo");
            parsedField15 = ly.ConfigValues.read(type15, values.get("lineupType"), fileName + ":行" + lineNumber + ":字段 lineupType");
            parsedField16 = ly.ConfigValues.read(type16, values.get("appointedNPC"), fileName + ":行" + lineNumber + ":字段 appointedNPC");
            parsedField17 = ly.ConfigValues.read(type17, values.get("defaultSpEnergy"), fileName + ":行" + lineNumber + ":字段 defaultSpEnergy");
            parsedField18 = ly.ConfigValues.read(type18, values.get("spEnergyCoe"), fileName + ":行" + lineNumber + ":字段 spEnergyCoe");
            parsedField19 = ly.ConfigValues.read(type19, values.get("defaultEnergy"), fileName + ":行" + lineNumber + ":字段 defaultEnergy");
            parsedField20 = ly.ConfigValues.read(type20, values.get("defaultEnergyRecover"), fileName + ":行" + lineNumber + ":字段 defaultEnergyRecover");
            parsedField21 = ly.ConfigValues.read(type21, values.get("defaultEnergyRaise"), fileName + ":行" + lineNumber + ":字段 defaultEnergyRaise");
            parsedField22 = ly.ConfigValues.read(type22, values.get("defaultEnergyRecoverMax"), fileName + ":行" + lineNumber + ":字段 defaultEnergyRecoverMax");
            parsedField23 = ly.ConfigValues.read(type23, values.get("defaultEnergyBar"), fileName + ":行" + lineNumber + ":字段 defaultEnergyBar");
            parsedField24 = ly.ConfigValues.read(type24, values.get("maxMember"), fileName + ":行" + lineNumber + ":字段 maxMember");
            parsedField25 = ly.ConfigValues.read(type25, values.get("sceneControlResource"), fileName + ":行" + lineNumber + ":字段 sceneControlResource");
            parsedField26 = ly.ConfigValues.read(type26, values.get("bossPos"), fileName + ":行" + lineNumber + ":字段 bossPos");
            parsedField27 = ly.ConfigValues.read(type27, values.get("npcType"), fileName + ":行" + lineNumber + ":字段 npcType");
            parsedField28 = ly.ConfigValues.read(type28, values.get("sceneNpc_1"), fileName + ":行" + lineNumber + ":字段 sceneNpc_1");
            parsedField29 = ly.ConfigValues.read(type29, values.get("sceneNpc_2"), fileName + ":行" + lineNumber + ":字段 sceneNpc_2");
            parsedField30 = ly.ConfigValues.read(type30, values.get("sceneNpc_3"), fileName + ":行" + lineNumber + ":字段 sceneNpc_3");
            parsedField31 = ly.ConfigValues.read(type31, values.get("sceneNpc_4"), fileName + ":行" + lineNumber + ":字段 sceneNpc_4");
            parsedField32 = ly.ConfigValues.read(type32, values.get("sceneNpc_5"), fileName + ":行" + lineNumber + ":字段 sceneNpc_5");
            parsedField33 = ly.ConfigValues.read(type33, values.get("battlePosId"), fileName + ":行" + lineNumber + ":字段 battlePosId");
            parsedField34 = ly.ConfigValues.read(type34, values.get("knockPosition_a"), fileName + ":行" + lineNumber + ":字段 knockPosition_a");
            parsedField35 = ly.ConfigValues.read(type35, values.get("knockPosition_b"), fileName + ":行" + lineNumber + ":字段 knockPosition_b");
            parsedField36 = ly.ConfigValues.read(type36, values.get("sceneObjects"), fileName + ":行" + lineNumber + ":字段 sceneObjects");
            parsedField37 = ly.ConfigValues.read(type37, values.get("bonusGroup"), fileName + ":行" + lineNumber + ":字段 bonusGroup");
            parsedField38 = ly.ConfigValues.read(type38, values.get("fixedBonusGroup"), fileName + ":行" + lineNumber + ":字段 fixedBonusGroup");
            parsedField39 = ly.ConfigValues.read(type39, values.get("eventIds"), fileName + ":行" + lineNumber + ":字段 eventIds");
            parsedField40 = ly.ConfigValues.read(type40, values.get("changeInfo"), fileName + ":行" + lineNumber + ":字段 changeInfo");
            parsedField41 = ly.ConfigValues.read(type41, values.get("endInfo"), fileName + ":行" + lineNumber + ":字段 endInfo");
            parsedField42 = ly.ConfigValues.read(type42, values.get("dropGroup"), fileName + ":行" + lineNumber + ":字段 dropGroup");
            parsedField43 = ly.ConfigValues.read(type43, values.get("starType"), fileName + ":行" + lineNumber + ":字段 starType");
            parsedField44 = ly.ConfigValues.read(type44, values.get("starList"), fileName + ":行" + lineNumber + ":字段 starList");
            parsedField45 = ly.ConfigValues.read(type45, values.get("dropList"), fileName + ":行" + lineNumber + ":字段 dropList");
            parsedField46 = ly.ConfigValues.read(type46, values.get("exp"), fileName + ":行" + lineNumber + ":字段 exp");
            parsedField47 = ly.ConfigValues.read(type47, values.get("gold"), fileName + ":行" + lineNumber + ":字段 gold");
            parsedField48 = ly.ConfigValues.read(type48, values.get("firstDrop"), fileName + ":行" + lineNumber + ":字段 firstDrop");
            parsedField49 = ly.ConfigValues.read(type49, values.get("summonType"), fileName + ":行" + lineNumber + ":字段 summonType");
            parsedField50 = ly.ConfigValues.read(type50, values.get("sceneSkills"), fileName + ":行" + lineNumber + ":字段 sceneSkills");
            parsedField51 = ly.ConfigValues.read(type51, values.get("triggerPro1"), fileName + ":行" + lineNumber + ":字段 triggerPro1");
            parsedField52 = ly.ConfigValues.read(type52, values.get("triggerStage1"), fileName + ":行" + lineNumber + ":字段 triggerStage1");
            parsedField53 = ly.ConfigValues.read(type53, values.get("triggerPro2"), fileName + ":行" + lineNumber + ":字段 triggerPro2");
            parsedField54 = ly.ConfigValues.read(type54, values.get("triggerStage2"), fileName + ":行" + lineNumber + ":字段 triggerStage2");
            parsedField55 = ly.ConfigValues.read(type55, values.get("light"), fileName + ":行" + lineNumber + ":字段 light");
            parsedField56 = ly.ConfigValues.read(type56, values.get("scenePos"), fileName + ":行" + lineNumber + ":字段 scenePos");
            parsedField57 = ly.ConfigValues.read(type57, values.get("scenePoint"), fileName + ":行" + lineNumber + ":字段 scenePoint");
            parsedField58 = ly.ConfigValues.read(type58, values.get("hintType"), fileName + ":行" + lineNumber + ":字段 hintType");
            parsedField59 = ly.ConfigValues.read(type59, values.get("sceneHint"), fileName + ":行" + lineNumber + ":字段 sceneHint");
            parsedField60 = ly.ConfigValues.read(type60, values.get("hintPic"), fileName + ":行" + lineNumber + ":字段 hintPic");
            parsedField61 = ly.ConfigValues.read(type61, values.get("endShow"), fileName + ":行" + lineNumber + ":字段 endShow");
            parsedField62 = ly.ConfigValues.read(type62, values.get("isCloseSpeed"), fileName + ":行" + lineNumber + ":字段 isCloseSpeed");
            parsedField63 = ly.ConfigValues.read(type63, values.get("isCloseAuto"), fileName + ":行" + lineNumber + ":字段 isCloseAuto");
            parsedField64 = ly.ConfigValues.read(type64, values.get("isClosePerformance"), fileName + ":行" + lineNumber + ":字段 isClosePerformance");
            parsedField65 = ly.ConfigValues.read(type65, values.get("isOpenSpecialUi"), fileName + ":行" + lineNumber + ":字段 isOpenSpecialUi");
            parsedField66 = ly.ConfigValues.read(type66, values.get("battleStartUiTpye"), fileName + ":行" + lineNumber + ":字段 battleStartUiTpye");
            parsedField67 = ly.ConfigValues.read(type67, values.get("showSelfBlood"), fileName + ":行" + lineNumber + ":字段 showSelfBlood");
            parsedField68 = ly.ConfigValues.read(type68, values.get("tryAgain"), fileName + ":行" + lineNumber + ":字段 tryAgain");
            parsedField69 = ly.ConfigValues.read(type69, values.get("sceneTimeId"), fileName + ":行" + lineNumber + ":字段 sceneTimeId");
            parsedField70 = ly.ConfigValues.read(type70, values.get("showDetail"), fileName + ":行" + lineNumber + ":字段 showDetail");
            parsedField71 = ly.ConfigValues.read(type71, values.get("changeHeroTime"), fileName + ":行" + lineNumber + ":字段 changeHeroTime");
            parsedField72 = ly.ConfigValues.read(type72, values.get("isShare"), fileName + ":行" + lineNumber + ":字段 isShare");
            parsedField73 = ly.ConfigValues.read(type73, values.get("strategy"), fileName + ":行" + lineNumber + ":字段 strategy");
            parsedField74 = ly.ConfigValues.read(type74, values.get("autoSaveLineup"), fileName + ":行" + lineNumber + ":字段 autoSaveLineup");
            parsedField75 = ly.ConfigValues.read(type75, values.get("isTryPlay"), fileName + ":行" + lineNumber + ":字段 isTryPlay");
            parsedField76 = ly.ConfigValues.read(type76, values.get("isChangeSkin"), fileName + ":行" + lineNumber + ":字段 isChangeSkin");
            parsedField77 = ly.ConfigValues.read(type77, values.get("blockBattleConvergeId"), fileName + ":行" + lineNumber + ":字段 blockBattleConvergeId");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SceneInfoConfig config = new SceneInfoConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24, parsedField25, parsedField26, parsedField27, parsedField28, parsedField29, parsedField30, parsedField31, parsedField32, parsedField33, parsedField34, parsedField35, parsedField36, parsedField37, parsedField38, parsedField39, parsedField40, parsedField41, parsedField42, parsedField43, parsedField44, parsedField45, parsedField46, parsedField47, parsedField48, parsedField49, parsedField50, parsedField51, parsedField52, parsedField53, parsedField54, parsedField55, parsedField56, parsedField57, parsedField58, parsedField59, parsedField60, parsedField61, parsedField62, parsedField63, parsedField64, parsedField65, parsedField66, parsedField67, parsedField68, parsedField69, parsedField70, parsedField71, parsedField72, parsedField73, parsedField74, parsedField75, parsedField76, parsedField77);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SceneInfoConfig config : configList) config.afterLoad();
      new SceneInfoConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SceneInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SceneInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "sceneInfo.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
