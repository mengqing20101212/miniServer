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
 * File: NpcConfigManager
 */
public class NpcConfigManager implements InterfaceConfigManagerProxy {
  public static NpcConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(NpcConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new NpcConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "npc.xml"; }

  public static class NpcConfigManagerImpl extends AbstractConfigManger {
    private List<NpcConfig> configList = List.of();
    private Map<Integer, NpcConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type24 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type25 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type26 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type27 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type28 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type29 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type30 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type31 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type32 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type33 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type34 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type35 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type36 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type37 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type38 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type39 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type40 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      NpcConfigChecker checker = new NpcConfigChecker();
      List<NpcConfig> newList = new ArrayList<>();
      Map<Integer, NpcConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          int parsedField15 = 0;
          int parsedField16 = 0;
          int parsedField17 = 0;
          int parsedField18 = 0;
          String parsedField19 = null;
          int parsedField20 = 0;
          int parsedField21 = 0;
          int parsedField22 = 0;
          String parsedField23 = null;
          int parsedField24 = 0;
          boolean parsedField25 = false;
          int parsedField26 = 0;
          String parsedField27 = null;
          int parsedField28 = 0;
          String parsedField29 = null;
          String parsedField30 = null;
          int parsedField31 = 0;
          int parsedField32 = 0;
          String parsedField33 = null;
          String parsedField34 = null;
          int parsedField35 = 0;
          String parsedField36 = null;
          int parsedField37 = 0;
          int parsedField38 = 0;
          int parsedField39 = 0;
          int parsedField40 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
            parsedField2 = ly.ConfigValues.read(type2, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField3 = ly.ConfigValues.read(type3, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField4 = ly.ConfigValues.read(type4, values.get("star"), fileName + ":行" + lineNumber + ":字段 star");
            parsedField5 = ly.ConfigValues.read(type5, values.get("maxHP"), fileName + ":行" + lineNumber + ":字段 maxHP");
            parsedField6 = ly.ConfigValues.read(type6, values.get("attack"), fileName + ":行" + lineNumber + ":字段 attack");
            parsedField7 = ly.ConfigValues.read(type7, values.get("defence"), fileName + ":行" + lineNumber + ":字段 defence");
            parsedField8 = ly.ConfigValues.read(type8, values.get("speed"), fileName + ":行" + lineNumber + ":字段 speed");
            parsedField9 = ly.ConfigValues.read(type9, values.get("crit"), fileName + ":行" + lineNumber + ":字段 crit");
            parsedField10 = ly.ConfigValues.read(type10, values.get("critRatio"), fileName + ":行" + lineNumber + ":字段 critRatio");
            parsedField11 = ly.ConfigValues.read(type11, values.get("effectHit"), fileName + ":行" + lineNumber + ":字段 effectHit");
            parsedField12 = ly.ConfigValues.read(type12, values.get("effectDodge"), fileName + ":行" + lineNumber + ":字段 effectDodge");
            parsedField13 = ly.ConfigValues.read(type13, values.get("spCoe"), fileName + ":行" + lineNumber + ":字段 spCoe");
            parsedField14 = ly.ConfigValues.read(type14, values.get("skill_1"), fileName + ":行" + lineNumber + ":字段 skill_1");
            parsedField15 = ly.ConfigValues.read(type15, values.get("skill_2"), fileName + ":行" + lineNumber + ":字段 skill_2");
            parsedField16 = ly.ConfigValues.read(type16, values.get("skill_3"), fileName + ":行" + lineNumber + ":字段 skill_3");
            parsedField17 = ly.ConfigValues.read(type17, values.get("skill_s1"), fileName + ":行" + lineNumber + ":字段 skill_s1");
            parsedField18 = ly.ConfigValues.read(type18, values.get("skill_s2"), fileName + ":行" + lineNumber + ":字段 skill_s2");
            parsedField19 = ly.ConfigValues.read(type19, values.get("aiName"), fileName + ":行" + lineNumber + ":字段 aiName");
            parsedField20 = ly.ConfigValues.read(type20, values.get("modelId"), fileName + ":行" + lineNumber + ":字段 modelId");
            parsedField21 = ly.ConfigValues.read(type21, values.get("headResource_3"), fileName + ":行" + lineNumber + ":字段 headResource_3");
            parsedField22 = ly.ConfigValues.read(type22, values.get("headResource_4"), fileName + ":行" + lineNumber + ":字段 headResource_4");
            parsedField23 = ly.ConfigValues.read(type23, values.get("sSkillCutUp"), fileName + ":行" + lineNumber + ":字段 sSkillCutUp");
            parsedField24 = ly.ConfigValues.read(type24, values.get("isBoss"), fileName + ":行" + lineNumber + ":字段 isBoss");
            parsedField25 = ly.ConfigValues.read(type25, values.get("canRun"), fileName + ":行" + lineNumber + ":字段 canRun");
            parsedField26 = ly.ConfigValues.read(type26, values.get("relateId"), fileName + ":行" + lineNumber + ":字段 relateId");
            parsedField27 = ly.ConfigValues.read(type27, values.get("extraSkillInfo"), fileName + ":行" + lineNumber + ":字段 extraSkillInfo");
            parsedField28 = ly.ConfigValues.read(type28, values.get("forcedAICD"), fileName + ":行" + lineNumber + ":字段 forcedAICD");
            parsedField29 = ly.ConfigValues.read(type29, values.get("skills"), fileName + ":行" + lineNumber + ":字段 skills");
            parsedField30 = ly.ConfigValues.read(type30, values.get("sSkills"), fileName + ":行" + lineNumber + ":字段 sSkills");
            parsedField31 = ly.ConfigValues.read(type31, values.get("npcType"), fileName + ":行" + lineNumber + ":字段 npcType");
            parsedField32 = ly.ConfigValues.read(type32, values.get("entityTags"), fileName + ":行" + lineNumber + ":字段 entityTags");
            parsedField33 = ly.ConfigValues.read(type33, values.get("sSkipCutUp"), fileName + ":行" + lineNumber + ":字段 sSkipCutUp");
            parsedField34 = ly.ConfigValues.read(type34, values.get("changeColorInfo"), fileName + ":行" + lineNumber + ":字段 changeColorInfo");
            parsedField35 = ly.ConfigValues.read(type35, values.get("colorType"), fileName + ":行" + lineNumber + ":字段 colorType");
            parsedField36 = ly.ConfigValues.read(type36, values.get("ShaderFresnel"), fileName + ":行" + lineNumber + ":字段 ShaderFresnel");
            parsedField37 = ly.ConfigValues.read(type37, values.get("heroType"), fileName + ":行" + lineNumber + ":字段 heroType");
            parsedField38 = ly.ConfigValues.read(type38, values.get("quality"), fileName + ":行" + lineNumber + ":字段 quality");
            parsedField39 = ly.ConfigValues.read(type39, values.get("characterType"), fileName + ":行" + lineNumber + ":字段 characterType");
            parsedField40 = ly.ConfigValues.read(type40, values.get("awakenLv"), fileName + ":行" + lineNumber + ":字段 awakenLv");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          NpcConfig config = new NpcConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24, parsedField25, parsedField26, parsedField27, parsedField28, parsedField29, parsedField30, parsedField31, parsedField32, parsedField33, parsedField34, parsedField35, parsedField36, parsedField37, parsedField38, parsedField39, parsedField40);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (NpcConfig config : configList) config.afterLoad();
      new NpcConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<NpcConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, NpcConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "npc.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
