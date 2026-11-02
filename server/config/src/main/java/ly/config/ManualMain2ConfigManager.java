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
 * File: ManualMain2ConfigManager
 */
public class ManualMain2ConfigManager implements InterfaceConfigManagerProxy {
  public static ManualMain2ConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ManualMain2ConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ManualMain2ConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "manualMain2.xml"; }

  public static class ManualMain2ConfigManagerImpl extends AbstractConfigManger {
    private List<ManualMain2Config> configList = List.of();
    private Map<Integer, ManualMain2Config> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      ManualMain2ConfigChecker checker = new ManualMain2ConfigChecker();
      List<ManualMain2Config> newList = new ArrayList<>();
      Map<Integer, ManualMain2Config> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          String parsedField3 = null;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          String parsedField8 = null;
          int parsedField9 = 0;
          int parsedField10 = 0;
          String parsedField11 = null;
          String parsedField12 = null;
          String parsedField13 = null;
          int parsedField14 = 0;
          int parsedField15 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("dailyNum"), fileName + ":行" + lineNumber + ":字段 dailyNum");
            parsedField2 = ly.ConfigValues.read(type2, values.get("page"), fileName + ":行" + lineNumber + ":字段 page");
            parsedField3 = ly.ConfigValues.read(type3, values.get("showNum"), fileName + ":行" + lineNumber + ":字段 showNum");
            parsedField4 = ly.ConfigValues.read(type4, values.get("priority"), fileName + ":行" + lineNumber + ":字段 priority");
            parsedField5 = ly.ConfigValues.read(type5, values.get("questType"), fileName + ":行" + lineNumber + ":字段 questType");
            parsedField6 = ly.ConfigValues.read(type6, values.get("condition"), fileName + ":行" + lineNumber + ":字段 condition");
            parsedField7 = ly.ConfigValues.read(type7, values.get("weight"), fileName + ":行" + lineNumber + ":字段 weight");
            parsedField8 = ly.ConfigValues.read(type8, values.get("rewardShow"), fileName + ":行" + lineNumber + ":字段 rewardShow");
            parsedField9 = ly.ConfigValues.read(type9, values.get("drop"), fileName + ":行" + lineNumber + ":字段 drop");
            parsedField10 = ly.ConfigValues.read(type10, values.get("redirectionId"), fileName + ":行" + lineNumber + ":字段 redirectionId");
            parsedField11 = ly.ConfigValues.read(type11, values.get("dailyNum2"), fileName + ":行" + lineNumber + ":字段 dailyNum2");
            parsedField12 = ly.ConfigValues.read(type12, values.get("nameIcon"), fileName + ":行" + lineNumber + ":字段 nameIcon");
            parsedField13 = ly.ConfigValues.read(type13, values.get("dailyIcon"), fileName + ":行" + lineNumber + ":字段 dailyIcon");
            parsedField14 = ly.ConfigValues.read(type14, values.get("typeIcon"), fileName + ":行" + lineNumber + ":字段 typeIcon");
            parsedField15 = ly.ConfigValues.read(type15, values.get("lvShow"), fileName + ":行" + lineNumber + ":字段 lvShow");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ManualMain2Config config = new ManualMain2Config(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ManualMain2Config config : configList) config.afterLoad();
      new ManualMain2ConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ManualMain2Config> getConfigList() {
      return configList;
    }

    public Map<Integer, ManualMain2Config> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "manualMain2.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
