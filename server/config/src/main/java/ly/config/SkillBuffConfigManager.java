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
 * File: SkillBuffConfigManager
 */
public class SkillBuffConfigManager implements InterfaceConfigManagerProxy {
  public static SkillBuffConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SkillBuffConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SkillBuffConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "skillBuff.xml"; }

  public static class SkillBuffConfigManagerImpl extends AbstractConfigManger {
    private List<SkillBuffConfig> configList = List.of();
    private Map<Integer, SkillBuffConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type24 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type25 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type26 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type27 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type28 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type29 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type30 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type31 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type32 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type33 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type34 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type35 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type36 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type37 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type38 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type39 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type40 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type41 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type42 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type43 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type44 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type45 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type46 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type47 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type48 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type49 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type50 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type51 = ly.ConfigValueType.parse("float");
      ly.ConfigValueType type52 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type53 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type54 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type55 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type56 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type57 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type58 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type59 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type60 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type61 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type62 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type63 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type64 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type65 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type66 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type67 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      SkillBuffConfigChecker checker = new SkillBuffConfigChecker();
      List<SkillBuffConfig> newList = new ArrayList<>();
      Map<Integer, SkillBuffConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          int parsedField5 = 0;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          String parsedField10 = null;
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          int parsedField15 = 0;
          int parsedField16 = 0;
          int parsedField17 = 0;
          int parsedField18 = 0;
          int parsedField19 = 0;
          int parsedField20 = 0;
          int parsedField21 = 0;
          int parsedField22 = 0;
          String parsedField23 = null;
          int parsedField24 = 0;
          int parsedField25 = 0;
          int parsedField26 = 0;
          int parsedField27 = 0;
          int parsedField28 = 0;
          int parsedField29 = 0;
          int parsedField30 = 0;
          int parsedField31 = 0;
          String parsedField32 = null;
          int parsedField33 = 0;
          int parsedField34 = 0;
          int parsedField35 = 0;
          int parsedField36 = 0;
          int parsedField37 = 0;
          String parsedField38 = null;
          String parsedField39 = null;
          int parsedField40 = 0;
          String parsedField41 = null;
          String parsedField42 = null;
          String parsedField43 = null;
          int parsedField44 = 0;
          int parsedField45 = 0;
          String parsedField46 = null;
          String parsedField47 = null;
          String parsedField48 = null;
          String parsedField49 = null;
          String parsedField50 = null;
          float parsedField51 = 0F;
          String parsedField52 = null;
          String parsedField53 = null;
          String parsedField54 = null;
          String parsedField55 = null;
          String parsedField56 = null;
          String parsedField57 = null;
          String parsedField58 = null;
          String parsedField59 = null;
          String parsedField60 = null;
          String parsedField61 = null;
          int parsedField62 = 0;
          int parsedField63 = 0;
          int parsedField64 = 0;
          int parsedField65 = 0;
          String parsedField66 = null;
          String parsedField67 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("effectType"), fileName + ":行" + lineNumber + ":字段 effectType");
            parsedField2 = ly.ConfigValues.read(type2, values.get("targetType"), fileName + ":行" + lineNumber + ":字段 targetType");
            parsedField3 = ly.ConfigValues.read(type3, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField4 = ly.ConfigValues.read(type4, values.get("description"), fileName + ":行" + lineNumber + ":字段 description");
            parsedField5 = ly.ConfigValues.read(type5, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
            parsedField6 = ly.ConfigValues.read(type6, values.get("suitId"), fileName + ":行" + lineNumber + ":字段 suitId");
            parsedField7 = ly.ConfigValues.read(type7, values.get("display"), fileName + ":行" + lineNumber + ":字段 display");
            parsedField8 = ly.ConfigValues.read(type8, values.get("displayType"), fileName + ":行" + lineNumber + ":字段 displayType");
            parsedField9 = ly.ConfigValues.read(type9, values.get("targetTypeEx"), fileName + ":行" + lineNumber + ":字段 targetTypeEx");
            parsedField10 = ly.ConfigValues.read(type10, values.get("targetTypeEx2"), fileName + ":行" + lineNumber + ":字段 targetTypeEx2");
            parsedField11 = ly.ConfigValues.read(type11, values.get("originSkillGroupId"), fileName + ":行" + lineNumber + ":字段 originSkillGroupId");
            parsedField12 = ly.ConfigValues.read(type12, values.get("banPassiveType"), fileName + ":行" + lineNumber + ":字段 banPassiveType");
            parsedField13 = ly.ConfigValues.read(type13, values.get("isPassive"), fileName + ":行" + lineNumber + ":字段 isPassive");
            parsedField14 = ly.ConfigValues.read(type14, values.get("isCircuit"), fileName + ":行" + lineNumber + ":字段 isCircuit");
            parsedField15 = ly.ConfigValues.read(type15, values.get("isTriggerCircuit"), fileName + ":行" + lineNumber + ":字段 isTriggerCircuit");
            parsedField16 = ly.ConfigValues.read(type16, values.get("isTriggerPassiveSkill"), fileName + ":行" + lineNumber + ":字段 isTriggerPassiveSkill");
            parsedField17 = ly.ConfigValues.read(type17, values.get("casterStrikeFlyTriggerFlag"), fileName + ":行" + lineNumber + ":字段 casterStrikeFlyTriggerFlag");
            parsedField18 = ly.ConfigValues.read(type18, values.get("targetStrikeFlyTriggerFlag"), fileName + ":行" + lineNumber + ":字段 targetStrikeFlyTriggerFlag");
            parsedField19 = ly.ConfigValues.read(type19, values.get("targetStrikeFlyAddFlag"), fileName + ":行" + lineNumber + ":字段 targetStrikeFlyAddFlag");
            parsedField20 = ly.ConfigValues.read(type20, values.get("mutexType"), fileName + ":行" + lineNumber + ":字段 mutexType");
            parsedField21 = ly.ConfigValues.read(type21, values.get("mutexPriority"), fileName + ":行" + lineNumber + ":字段 mutexPriority");
            parsedField22 = ly.ConfigValues.read(type22, values.get("rangeType"), fileName + ":行" + lineNumber + ":字段 rangeType");
            parsedField23 = ly.ConfigValues.read(type23, values.get("buffInfluence"), fileName + ":行" + lineNumber + ":字段 buffInfluence");
            parsedField24 = ly.ConfigValues.read(type24, values.get("initStack"), fileName + ":行" + lineNumber + ":字段 initStack");
            parsedField25 = ly.ConfigValues.read(type25, values.get("baseStack"), fileName + ":行" + lineNumber + ":字段 baseStack");
            parsedField26 = ly.ConfigValues.read(type26, values.get("maxStack"), fileName + ":行" + lineNumber + ":字段 maxStack");
            parsedField27 = ly.ConfigValues.read(type27, values.get("coexist"), fileName + ":行" + lineNumber + ":字段 coexist");
            parsedField28 = ly.ConfigValues.read(type28, values.get("refreshStack"), fileName + ":行" + lineNumber + ":字段 refreshStack");
            parsedField29 = ly.ConfigValues.read(type29, values.get("addProType"), fileName + ":行" + lineNumber + ":字段 addProType");
            parsedField30 = ly.ConfigValues.read(type30, values.get("addPro"), fileName + ":行" + lineNumber + ":字段 addPro");
            parsedField31 = ly.ConfigValues.read(type31, values.get("continuousType"), fileName + ":行" + lineNumber + ":字段 continuousType");
            parsedField32 = ly.ConfigValues.read(type32, values.get("continuousValue"), fileName + ":行" + lineNumber + ":字段 continuousValue");
            parsedField33 = ly.ConfigValues.read(type33, values.get("triggerType"), fileName + ":行" + lineNumber + ":字段 triggerType");
            parsedField34 = ly.ConfigValues.read(type34, values.get("triggerInterval"), fileName + ":行" + lineNumber + ":字段 triggerInterval");
            parsedField35 = ly.ConfigValues.read(type35, values.get("triggerPro"), fileName + ":行" + lineNumber + ":字段 triggerPro");
            parsedField36 = ly.ConfigValues.read(type36, values.get("triggerLimitPerTurn"), fileName + ":行" + lineNumber + ":字段 triggerLimitPerTurn");
            parsedField37 = ly.ConfigValues.read(type37, values.get("triggerConsumeStack"), fileName + ":行" + lineNumber + ":字段 triggerConsumeStack");
            parsedField38 = ly.ConfigValues.read(type38, values.get("entityTagFilters"), fileName + ":行" + lineNumber + ":字段 entityTagFilters");
            parsedField39 = ly.ConfigValues.read(type39, values.get("breakType"), fileName + ":行" + lineNumber + ":字段 breakType");
            parsedField40 = ly.ConfigValues.read(type40, values.get("breakEffectPro"), fileName + ":行" + lineNumber + ":字段 breakEffectPro");
            parsedField41 = ly.ConfigValues.read(type41, values.get("breakEffects"), fileName + ":行" + lineNumber + ":字段 breakEffects");
            parsedField42 = ly.ConfigValues.read(type42, values.get("endEffects"), fileName + ":行" + lineNumber + ":字段 endEffects");
            parsedField43 = ly.ConfigValues.read(type43, values.get("consumeEffects"), fileName + ":行" + lineNumber + ":字段 consumeEffects");
            parsedField44 = ly.ConfigValues.read(type44, values.get("spCoa1"), fileName + ":行" + lineNumber + ":字段 spCoa1");
            parsedField45 = ly.ConfigValues.read(type45, values.get("spCoa2"), fileName + ":行" + lineNumber + ":字段 spCoa2");
            parsedField46 = ly.ConfigValues.read(type46, values.get("startPerformance"), fileName + ":行" + lineNumber + ":字段 startPerformance");
            parsedField47 = ly.ConfigValues.read(type47, values.get("runPerformance"), fileName + ":行" + lineNumber + ":字段 runPerformance");
            parsedField48 = ly.ConfigValues.read(type48, values.get("breakPerformance"), fileName + ":行" + lineNumber + ":字段 breakPerformance");
            parsedField49 = ly.ConfigValues.read(type49, values.get("missPerformance"), fileName + ":行" + lineNumber + ":字段 missPerformance");
            parsedField50 = ly.ConfigValues.read(type50, values.get("endPerformance"), fileName + ":行" + lineNumber + ":字段 endPerformance");
            parsedField51 = ly.ConfigValues.read(type51, values.get("triggerLength"), fileName + ":行" + lineNumber + ":字段 triggerLength");
            parsedField52 = ly.ConfigValues.read(type52, values.get("param_1"), fileName + ":行" + lineNumber + ":字段 param_1");
            parsedField53 = ly.ConfigValues.read(type53, values.get("param_2"), fileName + ":行" + lineNumber + ":字段 param_2");
            parsedField54 = ly.ConfigValues.read(type54, values.get("param_3"), fileName + ":行" + lineNumber + ":字段 param_3");
            parsedField55 = ly.ConfigValues.read(type55, values.get("param_4"), fileName + ":行" + lineNumber + ":字段 param_4");
            parsedField56 = ly.ConfigValues.read(type56, values.get("param_5"), fileName + ":行" + lineNumber + ":字段 param_5");
            parsedField57 = ly.ConfigValues.read(type57, values.get("param_6"), fileName + ":行" + lineNumber + ":字段 param_6");
            parsedField58 = ly.ConfigValues.read(type58, values.get("param_7"), fileName + ":行" + lineNumber + ":字段 param_7");
            parsedField59 = ly.ConfigValues.read(type59, values.get("param_8"), fileName + ":行" + lineNumber + ":字段 param_8");
            parsedField60 = ly.ConfigValues.read(type60, values.get("param_9"), fileName + ":行" + lineNumber + ":字段 param_9");
            parsedField61 = ly.ConfigValues.read(type61, values.get("param_10"), fileName + ":行" + lineNumber + ":字段 param_10");
            parsedField62 = ly.ConfigValues.read(type62, values.get("heroId"), fileName + ":行" + lineNumber + ":字段 heroId");
            parsedField63 = ly.ConfigValues.read(type63, values.get("skillSequence"), fileName + ":行" + lineNumber + ":字段 skillSequence");
            parsedField64 = ly.ConfigValues.read(type64, values.get("effectSequence"), fileName + ":行" + lineNumber + ":字段 effectSequence");
            parsedField65 = ly.ConfigValues.read(type65, values.get("isReAdd"), fileName + ":行" + lineNumber + ":字段 isReAdd");
            parsedField66 = ly.ConfigValues.read(type66, values.get("effectRenew"), fileName + ":行" + lineNumber + ":字段 effectRenew");
            parsedField67 = ly.ConfigValues.read(type67, values.get("modelPerformance"), fileName + ":行" + lineNumber + ":字段 modelPerformance");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SkillBuffConfig config = new SkillBuffConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24, parsedField25, parsedField26, parsedField27, parsedField28, parsedField29, parsedField30, parsedField31, parsedField32, parsedField33, parsedField34, parsedField35, parsedField36, parsedField37, parsedField38, parsedField39, parsedField40, parsedField41, parsedField42, parsedField43, parsedField44, parsedField45, parsedField46, parsedField47, parsedField48, parsedField49, parsedField50, parsedField51, parsedField52, parsedField53, parsedField54, parsedField55, parsedField56, parsedField57, parsedField58, parsedField59, parsedField60, parsedField61, parsedField62, parsedField63, parsedField64, parsedField65, parsedField66, parsedField67);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SkillBuffConfig config : configList) config.afterLoad();
      new SkillBuffConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SkillBuffConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SkillBuffConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "skillBuff.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
