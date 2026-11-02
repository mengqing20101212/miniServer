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
 * File: DungeonEventConfigManager
 */
public class DungeonEventConfigManager implements InterfaceConfigManagerProxy {
  public static DungeonEventConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(DungeonEventConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new DungeonEventConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "dungeonEvent.xml"; }

  public static class DungeonEventConfigManagerImpl extends AbstractConfigManger {
    private List<DungeonEventConfig> configList = List.of();
    private Map<Integer, DungeonEventConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      DungeonEventConfigChecker checker = new DungeonEventConfigChecker();
      List<DungeonEventConfig> newList = new ArrayList<>();
      Map<Integer, DungeonEventConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          String parsedField4 = null;
          int parsedField5 = 0;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          String parsedField9 = null;
          String parsedField10 = null;
          String parsedField11 = null;
          int parsedField12 = 0;
          String parsedField13 = null;
          int parsedField14 = 0;
          int parsedField15 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("groupId"), fileName + ":行" + lineNumber + ":字段 groupId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("groupNum"), fileName + ":行" + lineNumber + ":字段 groupNum");
            parsedField3 = ly.ConfigValues.read(type3, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField4 = ly.ConfigValues.read(type4, values.get("eventName"), fileName + ":行" + lineNumber + ":字段 eventName");
            parsedField5 = ly.ConfigValues.read(type5, values.get("eventNameRes"), fileName + ":行" + lineNumber + ":字段 eventNameRes");
            parsedField6 = ly.ConfigValues.read(type6, values.get("dis"), fileName + ":行" + lineNumber + ":字段 dis");
            parsedField7 = ly.ConfigValues.read(type7, values.get("baseRes"), fileName + ":行" + lineNumber + ":字段 baseRes");
            parsedField8 = ly.ConfigValues.read(type8, values.get("titleRes"), fileName + ":行" + lineNumber + ":字段 titleRes");
            parsedField9 = ly.ConfigValues.read(type9, values.get("contentRes"), fileName + ":行" + lineNumber + ":字段 contentRes");
            parsedField10 = ly.ConfigValues.read(type10, values.get("para1"), fileName + ":行" + lineNumber + ":字段 para1");
            parsedField11 = ly.ConfigValues.read(type11, values.get("para2"), fileName + ":行" + lineNumber + ":字段 para2");
            parsedField12 = ly.ConfigValues.read(type12, values.get("para3"), fileName + ":行" + lineNumber + ":字段 para3");
            parsedField13 = ly.ConfigValues.read(type13, values.get("showContent"), fileName + ":行" + lineNumber + ":字段 showContent");
            parsedField14 = ly.ConfigValues.read(type14, values.get("showType"), fileName + ":行" + lineNumber + ":字段 showType");
            parsedField15 = ly.ConfigValues.read(type15, values.get("baseResGray"), fileName + ":行" + lineNumber + ":字段 baseResGray");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          DungeonEventConfig config = new DungeonEventConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (DungeonEventConfig config : configList) config.afterLoad();
      new DungeonEventConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<DungeonEventConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, DungeonEventConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "dungeonEvent.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
