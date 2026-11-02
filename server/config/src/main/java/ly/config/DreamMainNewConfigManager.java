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
 * File: DreamMainNewConfigManager
 */
public class DreamMainNewConfigManager implements InterfaceConfigManagerProxy {
  public static DreamMainNewConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(DreamMainNewConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new DreamMainNewConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "dreamMainNew.xml"; }

  public static class DreamMainNewConfigManagerImpl extends AbstractConfigManger {
    private List<DreamMainNewConfig> configList = List.of();
    private Map<Integer, DreamMainNewConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      DreamMainNewConfigChecker checker = new DreamMainNewConfigChecker();
      List<DreamMainNewConfig> newList = new ArrayList<>();
      Map<Integer, DreamMainNewConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          String parsedField7 = null;
          String parsedField8 = null;
          String parsedField9 = null;
          String parsedField10 = null;
          String parsedField11 = null;
          String parsedField12 = null;
          String parsedField13 = null;
          String parsedField14 = null;
          int parsedField15 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("nextId"), fileName + ":行" + lineNumber + ":字段 nextId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField3 = ly.ConfigValues.read(type3, values.get("nameResource"), fileName + ":行" + lineNumber + ":字段 nameResource");
            parsedField4 = ly.ConfigValues.read(type4, values.get("bgResource"), fileName + ":行" + lineNumber + ":字段 bgResource");
            parsedField5 = ly.ConfigValues.read(type5, values.get("ticketId"), fileName + ":行" + lineNumber + ":字段 ticketId");
            parsedField6 = ly.ConfigValues.read(type6, values.get("ticketNum"), fileName + ":行" + lineNumber + ":字段 ticketNum");
            parsedField7 = ly.ConfigValues.read(type7, values.get("dropList"), fileName + ":行" + lineNumber + ":字段 dropList");
            parsedField8 = ly.ConfigValues.read(type8, values.get("sceneList"), fileName + ":行" + lineNumber + ":字段 sceneList");
            parsedField9 = ly.ConfigValues.read(type9, values.get("heroCount"), fileName + ":行" + lineNumber + ":字段 heroCount");
            parsedField10 = ly.ConfigValues.read(type10, values.get("mechanism"), fileName + ":行" + lineNumber + ":字段 mechanism");
            parsedField11 = ly.ConfigValues.read(type11, values.get("limit1"), fileName + ":行" + lineNumber + ":字段 limit1");
            parsedField12 = ly.ConfigValues.read(type12, values.get("limit2"), fileName + ":行" + lineNumber + ":字段 limit2");
            parsedField13 = ly.ConfigValues.read(type13, values.get("limit3"), fileName + ":行" + lineNumber + ":字段 limit3");
            parsedField14 = ly.ConfigValues.read(type14, values.get("limit4"), fileName + ":行" + lineNumber + ":字段 limit4");
            parsedField15 = ly.ConfigValues.read(type15, values.get("dropShow"), fileName + ":行" + lineNumber + ":字段 dropShow");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          DreamMainNewConfig config = new DreamMainNewConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (DreamMainNewConfig config : configList) config.afterLoad();
      new DreamMainNewConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<DreamMainNewConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, DreamMainNewConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "dreamMainNew.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
