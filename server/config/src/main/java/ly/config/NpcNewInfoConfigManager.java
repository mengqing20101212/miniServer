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
 * File: NpcNewInfoConfigManager
 */
public class NpcNewInfoConfigManager implements InterfaceConfigManagerProxy {
  public static NpcNewInfoConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(NpcNewInfoConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new NpcNewInfoConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "npcNewInfo.xml"; }

  public static class NpcNewInfoConfigManagerImpl extends AbstractConfigManger {
    private List<NpcNewInfoConfig> configList = List.of();
    private Map<Integer, NpcNewInfoConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
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
      ly.ConfigValueType type24 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type25 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type26 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type27 = ly.ConfigValueType.parse("bool");
      ly.ConfigValueType type28 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type29 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type30 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      NpcNewInfoConfigChecker checker = new NpcNewInfoConfigChecker();
      List<NpcNewInfoConfig> newList = new ArrayList<>();
      Map<Integer, NpcNewInfoConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          String parsedField10 = null;
          String parsedField11 = null;
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
          String parsedField24 = null;
          String parsedField25 = null;
          int parsedField26 = 0;
          boolean parsedField27 = false;
          String parsedField28 = null;
          int parsedField29 = 0;
          int parsedField30 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("beizhu"), fileName + ":行" + lineNumber + ":字段 beizhu");
            parsedField2 = ly.ConfigValues.read(type2, values.get("circuitName"), fileName + ":行" + lineNumber + ":字段 circuitName");
            parsedField3 = ly.ConfigValues.read(type3, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField4 = ly.ConfigValues.read(type4, values.get("genType"), fileName + ":行" + lineNumber + ":字段 genType");
            parsedField5 = ly.ConfigValues.read(type5, values.get("checkId"), fileName + ":行" + lineNumber + ":字段 checkId");
            parsedField6 = ly.ConfigValues.read(type6, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField7 = ly.ConfigValues.read(type7, values.get("star"), fileName + ":行" + lineNumber + ":字段 star");
            parsedField8 = ly.ConfigValues.read(type8, values.get("advance"), fileName + ":行" + lineNumber + ":字段 advance");
            parsedField9 = ly.ConfigValues.read(type9, values.get("awakenLv"), fileName + ":行" + lineNumber + ":字段 awakenLv");
            parsedField10 = ly.ConfigValues.read(type10, values.get("skillLv"), fileName + ":行" + lineNumber + ":字段 skillLv");
            parsedField11 = ly.ConfigValues.read(type11, values.get("sSkillLv"), fileName + ":行" + lineNumber + ":字段 sSkillLv");
            parsedField12 = ly.ConfigValues.read(type12, values.get("circuitLv"), fileName + ":行" + lineNumber + ":字段 circuitLv");
            parsedField13 = ly.ConfigValues.read(type13, values.get("circuitQuality"), fileName + ":行" + lineNumber + ":字段 circuitQuality");
            parsedField14 = ly.ConfigValues.read(type14, values.get("circuitInfo"), fileName + ":行" + lineNumber + ":字段 circuitInfo");
            parsedField15 = ly.ConfigValues.read(type15, values.get("maxHPCoe"), fileName + ":行" + lineNumber + ":字段 maxHPCoe");
            parsedField16 = ly.ConfigValues.read(type16, values.get("attackCoe"), fileName + ":行" + lineNumber + ":字段 attackCoe");
            parsedField17 = ly.ConfigValues.read(type17, values.get("defenceCoe"), fileName + ":行" + lineNumber + ":字段 defenceCoe");
            parsedField18 = ly.ConfigValues.read(type18, values.get("speedCoe"), fileName + ":行" + lineNumber + ":字段 speedCoe");
            parsedField19 = ly.ConfigValues.read(type19, values.get("critCoe"), fileName + ":行" + lineNumber + ":字段 critCoe");
            parsedField20 = ly.ConfigValues.read(type20, values.get("critRatioCoe"), fileName + ":行" + lineNumber + ":字段 critRatioCoe");
            parsedField21 = ly.ConfigValues.read(type21, values.get("effectHitCoe"), fileName + ":行" + lineNumber + ":字段 effectHitCoe");
            parsedField22 = ly.ConfigValues.read(type22, values.get("effectDodgeCoe"), fileName + ":行" + lineNumber + ":字段 effectDodgeCoe");
            parsedField23 = ly.ConfigValues.read(type23, values.get("skills"), fileName + ":行" + lineNumber + ":字段 skills");
            parsedField24 = ly.ConfigValues.read(type24, values.get("sSkills"), fileName + ":行" + lineNumber + ":字段 sSkills");
            parsedField25 = ly.ConfigValues.read(type25, values.get("aiName"), fileName + ":行" + lineNumber + ":字段 aiName");
            parsedField26 = ly.ConfigValues.read(type26, values.get("isBoss"), fileName + ":行" + lineNumber + ":字段 isBoss");
            parsedField27 = ly.ConfigValues.read(type27, values.get("canRun"), fileName + ":行" + lineNumber + ":字段 canRun");
            parsedField28 = ly.ConfigValues.read(type28, values.get("extraSkillInfo"), fileName + ":行" + lineNumber + ":字段 extraSkillInfo");
            parsedField29 = ly.ConfigValues.read(type29, values.get("npcType"), fileName + ":行" + lineNumber + ":字段 npcType");
            parsedField30 = ly.ConfigValues.read(type30, values.get("entityTags"), fileName + ":行" + lineNumber + ":字段 entityTags");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          NpcNewInfoConfig config = new NpcNewInfoConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24, parsedField25, parsedField26, parsedField27, parsedField28, parsedField29, parsedField30);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (NpcNewInfoConfig config : configList) config.afterLoad();
      new NpcNewInfoConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<NpcNewInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, NpcNewInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "npcNewInfo.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
