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
 * File: SocietyCleanCityConfigManager
 */
public class SocietyCleanCityConfigManager implements InterfaceConfigManagerProxy {
  public static SocietyCleanCityConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SocietyCleanCityConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SocietyCleanCityConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "societyCleanCity.xml"; }

  public static class SocietyCleanCityConfigManagerImpl extends AbstractConfigManger {
    private List<SocietyCleanCityConfig> configList = List.of();
    private Map<Integer, SocietyCleanCityConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      SocietyCleanCityConfigChecker checker = new SocietyCleanCityConfigChecker();
      List<SocietyCleanCityConfig> newList = new ArrayList<>();
      Map<Integer, SocietyCleanCityConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          String parsedField6 = null;
          String parsedField7 = null;
          String parsedField8 = null;
          String parsedField9 = null;
          String parsedField10 = null;
          String parsedField11 = null;
          String parsedField12 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("cell"), fileName + ":行" + lineNumber + ":字段 cell");
            parsedField2 = ly.ConfigValues.read(type2, values.get("progressdrop25"), fileName + ":行" + lineNumber + ":字段 progressdrop25");
            parsedField3 = ly.ConfigValues.read(type3, values.get("progressdrop50"), fileName + ":行" + lineNumber + ":字段 progressdrop50");
            parsedField4 = ly.ConfigValues.read(type4, values.get("progressdrop75"), fileName + ":行" + lineNumber + ":字段 progressdrop75");
            parsedField5 = ly.ConfigValues.read(type5, values.get("progressdrop100"), fileName + ":行" + lineNumber + ":字段 progressdrop100");
            parsedField6 = ly.ConfigValues.read(type6, values.get("rewardEvent"), fileName + ":行" + lineNumber + ":字段 rewardEvent");
            parsedField7 = ly.ConfigValues.read(type7, values.get("giveEvent"), fileName + ":行" + lineNumber + ":字段 giveEvent");
            parsedField8 = ly.ConfigValues.read(type8, values.get("greatReward"), fileName + ":行" + lineNumber + ":字段 greatReward");
            parsedField9 = ly.ConfigValues.read(type9, values.get("repairEvent"), fileName + ":行" + lineNumber + ":字段 repairEvent");
            parsedField10 = ly.ConfigValues.read(type10, values.get("storyEvent"), fileName + ":行" + lineNumber + ":字段 storyEvent");
            parsedField11 = ly.ConfigValues.read(type11, values.get("trapEvent"), fileName + ":行" + lineNumber + ":字段 trapEvent");
            parsedField12 = ly.ConfigValues.read(type12, values.get("bossEvent"), fileName + ":行" + lineNumber + ":字段 bossEvent");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SocietyCleanCityConfig config = new SocietyCleanCityConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SocietyCleanCityConfig config : configList) config.afterLoad();
      new SocietyCleanCityConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SocietyCleanCityConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SocietyCleanCityConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "societyCleanCity.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
