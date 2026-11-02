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
 * File: SkillEffectConfigManager
 */
public class SkillEffectConfigManager implements InterfaceConfigManagerProxy {
  public static SkillEffectConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SkillEffectConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SkillEffectConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "skillEffect.xml"; }

  public static class SkillEffectConfigManagerImpl extends AbstractConfigManger {
    private List<SkillEffectConfig> configList = List.of();
    private Map<Integer, SkillEffectConfig> configMap = Map.of();

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
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type24 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type25 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type26 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type27 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type28 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type29 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type30 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type31 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type32 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type33 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type34 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type35 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type36 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      SkillEffectConfigChecker checker = new SkillEffectConfigChecker();
      List<SkillEffectConfig> newList = new ArrayList<>();
      Map<Integer, SkillEffectConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          String parsedField10 = null;
          int parsedField11 = 0;
          String parsedField12 = null;
          int parsedField13 = 0;
          int parsedField14 = 0;
          String parsedField15 = null;
          String parsedField16 = null;
          int parsedField17 = 0;
          int parsedField18 = 0;
          String parsedField19 = null;
          String parsedField20 = null;
          String parsedField21 = null;
          String parsedField22 = null;
          String parsedField23 = null;
          String parsedField24 = null;
          String parsedField25 = null;
          String parsedField26 = null;
          String parsedField27 = null;
          String parsedField28 = null;
          String parsedField29 = null;
          String parsedField30 = null;
          String parsedField31 = null;
          String parsedField32 = null;
          int parsedField33 = 0;
          int parsedField34 = 0;
          int parsedField35 = 0;
          String parsedField36 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("effectType"), fileName + ":行" + lineNumber + ":字段 effectType");
            parsedField2 = ly.ConfigValues.read(type2, values.get("targetType"), fileName + ":行" + lineNumber + ":字段 targetType");
            parsedField3 = ly.ConfigValues.read(type3, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField4 = ly.ConfigValues.read(type4, values.get("description"), fileName + ":行" + lineNumber + ":字段 description");
            parsedField5 = ly.ConfigValues.read(type5, values.get("isTriggerCircuit"), fileName + ":行" + lineNumber + ":字段 isTriggerCircuit");
            parsedField6 = ly.ConfigValues.read(type6, values.get("isTriggerPassiveSkill"), fileName + ":行" + lineNumber + ":字段 isTriggerPassiveSkill");
            parsedField7 = ly.ConfigValues.read(type7, values.get("casterStrikeFlyTriggerFlag"), fileName + ":行" + lineNumber + ":字段 casterStrikeFlyTriggerFlag");
            parsedField8 = ly.ConfigValues.read(type8, values.get("targetStrikeFlyTriggerFlag"), fileName + ":行" + lineNumber + ":字段 targetStrikeFlyTriggerFlag");
            parsedField9 = ly.ConfigValues.read(type9, values.get("targetTypeEx"), fileName + ":行" + lineNumber + ":字段 targetTypeEx");
            parsedField10 = ly.ConfigValues.read(type10, values.get("targetTypeEx2"), fileName + ":行" + lineNumber + ":字段 targetTypeEx2");
            parsedField11 = ly.ConfigValues.read(type11, values.get("rangeType"), fileName + ":行" + lineNumber + ":字段 rangeType");
            parsedField12 = ly.ConfigValues.read(type12, values.get("buffInfluence"), fileName + ":行" + lineNumber + ":字段 buffInfluence");
            parsedField13 = ly.ConfigValues.read(type13, values.get("addProType"), fileName + ":行" + lineNumber + ":字段 addProType");
            parsedField14 = ly.ConfigValues.read(type14, values.get("addPro"), fileName + ":行" + lineNumber + ":字段 addPro");
            parsedField15 = ly.ConfigValues.read(type15, values.get("entityTagFilters"), fileName + ":行" + lineNumber + ":字段 entityTagFilters");
            parsedField16 = ly.ConfigValues.read(type16, values.get("endEffects"), fileName + ":行" + lineNumber + ":字段 endEffects");
            parsedField17 = ly.ConfigValues.read(type17, values.get("spCoa1"), fileName + ":行" + lineNumber + ":字段 spCoa1");
            parsedField18 = ly.ConfigValues.read(type18, values.get("spCoa2"), fileName + ":行" + lineNumber + ":字段 spCoa2");
            parsedField19 = ly.ConfigValues.read(type19, values.get("startPerformance"), fileName + ":行" + lineNumber + ":字段 startPerformance");
            parsedField20 = ly.ConfigValues.read(type20, values.get("runPerformance"), fileName + ":行" + lineNumber + ":字段 runPerformance");
            parsedField21 = ly.ConfigValues.read(type21, values.get("missPerformance"), fileName + ":行" + lineNumber + ":字段 missPerformance");
            parsedField22 = ly.ConfigValues.read(type22, values.get("endPerformance"), fileName + ":行" + lineNumber + ":字段 endPerformance");
            parsedField23 = ly.ConfigValues.read(type23, values.get("param_1"), fileName + ":行" + lineNumber + ":字段 param_1");
            parsedField24 = ly.ConfigValues.read(type24, values.get("param_2"), fileName + ":行" + lineNumber + ":字段 param_2");
            parsedField25 = ly.ConfigValues.read(type25, values.get("param_3"), fileName + ":行" + lineNumber + ":字段 param_3");
            parsedField26 = ly.ConfigValues.read(type26, values.get("param_4"), fileName + ":行" + lineNumber + ":字段 param_4");
            parsedField27 = ly.ConfigValues.read(type27, values.get("param_5"), fileName + ":行" + lineNumber + ":字段 param_5");
            parsedField28 = ly.ConfigValues.read(type28, values.get("param_6"), fileName + ":行" + lineNumber + ":字段 param_6");
            parsedField29 = ly.ConfigValues.read(type29, values.get("param_7"), fileName + ":行" + lineNumber + ":字段 param_7");
            parsedField30 = ly.ConfigValues.read(type30, values.get("param_8"), fileName + ":行" + lineNumber + ":字段 param_8");
            parsedField31 = ly.ConfigValues.read(type31, values.get("param_9"), fileName + ":行" + lineNumber + ":字段 param_9");
            parsedField32 = ly.ConfigValues.read(type32, values.get("param_10"), fileName + ":行" + lineNumber + ":字段 param_10");
            parsedField33 = ly.ConfigValues.read(type33, values.get("heroId"), fileName + ":行" + lineNumber + ":字段 heroId");
            parsedField34 = ly.ConfigValues.read(type34, values.get("skillSequence"), fileName + ":行" + lineNumber + ":字段 skillSequence");
            parsedField35 = ly.ConfigValues.read(type35, values.get("effectSequence"), fileName + ":行" + lineNumber + ":字段 effectSequence");
            parsedField36 = ly.ConfigValues.read(type36, values.get("effectRenew"), fileName + ":行" + lineNumber + ":字段 effectRenew");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SkillEffectConfig config = new SkillEffectConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24, parsedField25, parsedField26, parsedField27, parsedField28, parsedField29, parsedField30, parsedField31, parsedField32, parsedField33, parsedField34, parsedField35, parsedField36);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SkillEffectConfig config : configList) config.afterLoad();
      new SkillEffectConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SkillEffectConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SkillEffectConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "skillEffect.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
