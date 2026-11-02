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
 * File: Top_resources_setConfigManager
 */
public class Top_resources_setConfigManager implements InterfaceConfigManagerProxy {
  public static Top_resources_setConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(Top_resources_setConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new Top_resources_setConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "top_resources_set.xml"; }

  public static class Top_resources_setConfigManagerImpl extends AbstractConfigManger {
    private List<Top_resources_setConfig> configList = List.of();
    private Map<Integer, Top_resources_setConfig> configMap = Map.of();

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
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      Top_resources_setConfigChecker checker = new Top_resources_setConfigChecker();
      List<Top_resources_setConfig> newList = new ArrayList<>();
      Map<Integer, Top_resources_setConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          String parsedField6 = null;
          String parsedField7 = null;
          java.util.List<Integer> parsedField8 = null;
          java.util.List<Integer> parsedField9 = null;
          java.util.List<Integer> parsedField10 = null;
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("desc"), fileName + ":行" + lineNumber + ":字段 desc");
            parsedField2 = ly.ConfigValues.read(type2, values.get("title"), fileName + ":行" + lineNumber + ":字段 title");
            parsedField3 = ly.ConfigValues.read(type3, values.get("titleTypeJudgment"), fileName + ":行" + lineNumber + ":字段 titleTypeJudgment");
            parsedField4 = ly.ConfigValues.read(type4, values.get("showtime"), fileName + ":行" + lineNumber + ":字段 showtime");
            parsedField5 = ly.ConfigValues.read(type5, values.get("titleType"), fileName + ":行" + lineNumber + ":字段 titleType");
            parsedField6 = ly.ConfigValues.read(type6, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
            parsedField7 = ly.ConfigValues.read(type7, values.get("titleWord"), fileName + ":行" + lineNumber + ":字段 titleWord");
            parsedField8 = ly.ConfigValues.read(type8, values.get("show_type"), fileName + ":行" + lineNumber + ":字段 show_type");
            parsedField9 = ly.ConfigValues.read(type9, values.get("fastTurnID"), fileName + ":行" + lineNumber + ":字段 fastTurnID");
            parsedField10 = ly.ConfigValues.read(type10, values.get("activeType"), fileName + ":行" + lineNumber + ":字段 activeType");
            parsedField11 = ly.ConfigValues.read(type11, values.get("helpTurnId"), fileName + ":行" + lineNumber + ":字段 helpTurnId");
            parsedField12 = ly.ConfigValues.read(type12, values.get("helpdesc"), fileName + ":行" + lineNumber + ":字段 helpdesc");
            parsedField13 = ly.ConfigValues.read(type13, values.get("activityId"), fileName + ":行" + lineNumber + ":字段 activityId");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          Top_resources_setConfig config = new Top_resources_setConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (Top_resources_setConfig config : configList) config.afterLoad();
      new Top_resources_setConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<Top_resources_setConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, Top_resources_setConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "top_resources_set.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
