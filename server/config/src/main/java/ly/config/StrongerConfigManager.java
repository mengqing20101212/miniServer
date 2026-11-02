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
 * File: StrongerConfigManager
 */
public class StrongerConfigManager implements InterfaceConfigManagerProxy {
  public static StrongerConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(StrongerConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new StrongerConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "stronger.xml"; }

  public static class StrongerConfigManagerImpl extends AbstractConfigManger {
    private List<StrongerConfig> configList = List.of();
    private Map<Integer, StrongerConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      StrongerConfigChecker checker = new StrongerConfigChecker();
      List<StrongerConfig> newList = new ArrayList<>();
      Map<Integer, StrongerConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          String parsedField4 = null;
          int parsedField5 = 0;
          int parsedField6 = 0;
          String parsedField7 = null;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          String parsedField12 = null;
          int parsedField13 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("resType"), fileName + ":行" + lineNumber + ":字段 resType");
            parsedField2 = ly.ConfigValues.read(type2, values.get("resName"), fileName + ":行" + lineNumber + ":字段 resName");
            parsedField3 = ly.ConfigValues.read(type3, values.get("resPic"), fileName + ":行" + lineNumber + ":字段 resPic");
            parsedField4 = ly.ConfigValues.read(type4, values.get("resDes"), fileName + ":行" + lineNumber + ":字段 resDes");
            parsedField5 = ly.ConfigValues.read(type5, values.get("resSort"), fileName + ":行" + lineNumber + ":字段 resSort");
            parsedField6 = ly.ConfigValues.read(type6, values.get("playType"), fileName + ":行" + lineNumber + ":字段 playType");
            parsedField7 = ly.ConfigValues.read(type7, values.get("playName"), fileName + ":行" + lineNumber + ":字段 playName");
            parsedField8 = ly.ConfigValues.read(type8, values.get("playStar"), fileName + ":行" + lineNumber + ":字段 playStar");
            parsedField9 = ly.ConfigValues.read(type9, values.get("playJump"), fileName + ":行" + lineNumber + ":字段 playJump");
            parsedField10 = ly.ConfigValues.read(type10, values.get("playSort"), fileName + ":行" + lineNumber + ":字段 playSort");
            parsedField11 = ly.ConfigValues.read(type11, values.get("itemId"), fileName + ":行" + lineNumber + ":字段 itemId");
            parsedField12 = ly.ConfigValues.read(type12, values.get("itemName"), fileName + ":行" + lineNumber + ":字段 itemName");
            parsedField13 = ly.ConfigValues.read(type13, values.get("itemSort"), fileName + ":行" + lineNumber + ":字段 itemSort");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          StrongerConfig config = new StrongerConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (StrongerConfig config : configList) config.afterLoad();
      new StrongerConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<StrongerConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, StrongerConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "stronger.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
