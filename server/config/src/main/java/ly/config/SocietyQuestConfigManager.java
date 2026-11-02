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
 * File: SocietyQuestConfigManager
 */
public class SocietyQuestConfigManager implements InterfaceConfigManagerProxy {
  public static SocietyQuestConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SocietyQuestConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SocietyQuestConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "societyQuest.xml"; }

  public static class SocietyQuestConfigManagerImpl extends AbstractConfigManger {
    private List<SocietyQuestConfig> configList = List.of();
    private Map<Integer, SocietyQuestConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      SocietyQuestConfigChecker checker = new SocietyQuestConfigChecker();
      List<SocietyQuestConfig> newList = new ArrayList<>();
      Map<Integer, SocietyQuestConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          String parsedField6 = null;
          String parsedField7 = null;
          int parsedField8 = 0;
          int parsedField9 = 0;
          String parsedField10 = null;
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          String parsedField14 = null;
          int parsedField15 = 0;
          String parsedField16 = null;
          String parsedField17 = null;
          String parsedField18 = null;
          String parsedField19 = null;
          int parsedField20 = 0;
          int parsedField21 = 0;
          int parsedField22 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("questId"), fileName + ":行" + lineNumber + ":字段 questId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("questId2"), fileName + ":行" + lineNumber + ":字段 questId2");
            parsedField3 = ly.ConfigValues.read(type3, values.get("minLevel"), fileName + ":行" + lineNumber + ":字段 minLevel");
            parsedField4 = ly.ConfigValues.read(type4, values.get("maxLevel"), fileName + ":行" + lineNumber + ":字段 maxLevel");
            parsedField5 = ly.ConfigValues.read(type5, values.get("title"), fileName + ":行" + lineNumber + ":字段 title");
            parsedField6 = ly.ConfigValues.read(type6, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField7 = ly.ConfigValues.read(type7, values.get("name2"), fileName + ":行" + lineNumber + ":字段 name2");
            parsedField8 = ly.ConfigValues.read(type8, values.get("star"), fileName + ":行" + lineNumber + ":字段 star");
            parsedField9 = ly.ConfigValues.read(type9, values.get("rewardType"), fileName + ":行" + lineNumber + ":字段 rewardType");
            parsedField10 = ly.ConfigValues.read(type10, values.get("beizhu1"), fileName + ":行" + lineNumber + ":字段 beizhu1");
            parsedField11 = ly.ConfigValues.read(type11, values.get("weights"), fileName + ":行" + lineNumber + ":字段 weights");
            parsedField12 = ly.ConfigValues.read(type12, values.get("isCooperate"), fileName + ":行" + lineNumber + ":字段 isCooperate");
            parsedField13 = ly.ConfigValues.read(type13, values.get("isRare"), fileName + ":行" + lineNumber + ":字段 isRare");
            parsedField14 = ly.ConfigValues.read(type14, values.get("rewardShow"), fileName + ":行" + lineNumber + ":字段 rewardShow");
            parsedField15 = ly.ConfigValues.read(type15, values.get("drop"), fileName + ":行" + lineNumber + ":字段 drop");
            parsedField16 = ly.ConfigValues.read(type16, values.get("beizhu2"), fileName + ":行" + lineNumber + ":字段 beizhu2");
            parsedField17 = ly.ConfigValues.read(type17, values.get("beizhu3"), fileName + ":行" + lineNumber + ":字段 beizhu3");
            parsedField18 = ly.ConfigValues.read(type18, values.get("beizhu4"), fileName + ":行" + lineNumber + ":字段 beizhu4");
            parsedField19 = ly.ConfigValues.read(type19, values.get("beizhu5"), fileName + ":行" + lineNumber + ":字段 beizhu5");
            parsedField20 = ly.ConfigValues.read(type20, values.get("redirectionId"), fileName + ":行" + lineNumber + ":字段 redirectionId");
            parsedField21 = ly.ConfigValues.read(type21, values.get("redirectionId2"), fileName + ":行" + lineNumber + ":字段 redirectionId2");
            parsedField22 = ly.ConfigValues.read(type22, values.get("goundId"), fileName + ":行" + lineNumber + ":字段 goundId");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SocietyQuestConfig config = new SocietyQuestConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SocietyQuestConfig config : configList) config.afterLoad();
      new SocietyQuestConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SocietyQuestConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SocietyQuestConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "societyQuest.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
