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
 * File: JusticeStageConfigManager
 */
public class JusticeStageConfigManager implements InterfaceConfigManagerProxy {
  public static JusticeStageConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(JusticeStageConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new JusticeStageConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "justiceStage.xml"; }

  public static class JusticeStageConfigManagerImpl extends AbstractConfigManger {
    private List<JusticeStageConfig> configList = List.of();
    private Map<Integer, JusticeStageConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      JusticeStageConfigChecker checker = new JusticeStageConfigChecker();
      List<JusticeStageConfig> newList = new ArrayList<>();
      Map<Integer, JusticeStageConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          String parsedField5 = null;
          int parsedField6 = 0;
          int parsedField7 = 0;
          String parsedField8 = null;
          String parsedField9 = null;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          String parsedField14 = null;
          String parsedField15 = null;
          int parsedField16 = 0;
          int parsedField17 = 0;
          int parsedField18 = 0;
          int parsedField19 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField2 = ly.ConfigValues.read(type2, values.get("enemyInfo"), fileName + ":行" + lineNumber + ":字段 enemyInfo");
            parsedField3 = ly.ConfigValues.read(type3, values.get("enemyCircuitInfo"), fileName + ":行" + lineNumber + ":字段 enemyCircuitInfo");
            parsedField4 = ly.ConfigValues.read(type4, values.get("enemyPara"), fileName + ":行" + lineNumber + ":字段 enemyPara");
            parsedField5 = ly.ConfigValues.read(type5, values.get("sceneId"), fileName + ":行" + lineNumber + ":字段 sceneId");
            parsedField6 = ly.ConfigValues.read(type6, values.get("dropId"), fileName + ":行" + lineNumber + ":字段 dropId");
            parsedField7 = ly.ConfigValues.read(type7, values.get("firstDrop"), fileName + ":行" + lineNumber + ":字段 firstDrop");
            parsedField8 = ly.ConfigValues.read(type8, values.get("dropShow"), fileName + ":行" + lineNumber + ":字段 dropShow");
            parsedField9 = ly.ConfigValues.read(type9, values.get("hint"), fileName + ":行" + lineNumber + ":字段 hint");
            parsedField10 = ly.ConfigValues.read(type10, values.get("tipPic"), fileName + ":行" + lineNumber + ":字段 tipPic");
            parsedField11 = ly.ConfigValues.read(type11, values.get("background"), fileName + ":行" + lineNumber + ":字段 background");
            parsedField12 = ly.ConfigValues.read(type12, values.get("backgroundIn"), fileName + ":行" + lineNumber + ":字段 backgroundIn");
            parsedField13 = ly.ConfigValues.read(type13, values.get("heroPicId"), fileName + ":行" + lineNumber + ":字段 heroPicId");
            parsedField14 = ly.ConfigValues.read(type14, values.get("recommendHeroIds"), fileName + ":行" + lineNumber + ":字段 recommendHeroIds");
            parsedField15 = ly.ConfigValues.read(type15, values.get("recommendTypes"), fileName + ":行" + lineNumber + ":字段 recommendTypes");
            parsedField16 = ly.ConfigValues.read(type16, values.get("avgLineupLevel"), fileName + ":行" + lineNumber + ":字段 avgLineupLevel");
            parsedField17 = ly.ConfigValues.read(type17, values.get("dropDay"), fileName + ":行" + lineNumber + ":字段 dropDay");
            parsedField18 = ly.ConfigValues.read(type18, values.get("dropDayShow"), fileName + ":行" + lineNumber + ":字段 dropDayShow");
            parsedField19 = ly.ConfigValues.read(type19, values.get("targetLevel"), fileName + ":行" + lineNumber + ":字段 targetLevel");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          JusticeStageConfig config = new JusticeStageConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (JusticeStageConfig config : configList) config.afterLoad();
      new JusticeStageConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<JusticeStageConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, JusticeStageConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "justiceStage.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
