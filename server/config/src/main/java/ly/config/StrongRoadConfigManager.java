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
 * File: StrongRoadConfigManager
 */
public class StrongRoadConfigManager implements InterfaceConfigManagerProxy {
  public static StrongRoadConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(StrongRoadConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new StrongRoadConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "strongRoad.xml"; }

  public static class StrongRoadConfigManagerImpl extends AbstractConfigManger {
    private List<StrongRoadConfig> configList = List.of();
    private Map<Integer, StrongRoadConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      StrongRoadConfigChecker checker = new StrongRoadConfigChecker();
      List<StrongRoadConfig> newList = new ArrayList<>();
      Map<Integer, StrongRoadConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          int parsedField6 = 0;
          int parsedField7 = 0;
          String parsedField8 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField2 = ly.ConfigValues.read(type2, values.get("para"), fileName + ":行" + lineNumber + ":字段 para");
            parsedField3 = ly.ConfigValues.read(type3, values.get("iconShow"), fileName + ":行" + lineNumber + ":字段 iconShow");
            parsedField4 = ly.ConfigValues.read(type4, values.get("show"), fileName + ":行" + lineNumber + ":字段 show");
            parsedField5 = ly.ConfigValues.read(type5, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField6 = ly.ConfigValues.read(type6, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
            parsedField7 = ly.ConfigValues.read(type7, values.get("turnId"), fileName + ":行" + lineNumber + ":字段 turnId");
            parsedField8 = ly.ConfigValues.read(type8, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          StrongRoadConfig config = new StrongRoadConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (StrongRoadConfig config : configList) config.afterLoad();
      new StrongRoadConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<StrongRoadConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, StrongRoadConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "strongRoad.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
