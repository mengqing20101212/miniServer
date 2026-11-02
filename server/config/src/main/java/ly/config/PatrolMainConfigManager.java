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
 * File: PatrolMainConfigManager
 */
public class PatrolMainConfigManager implements InterfaceConfigManagerProxy {
  public static PatrolMainConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(PatrolMainConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new PatrolMainConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "patrolMain.xml"; }

  public static class PatrolMainConfigManagerImpl extends AbstractConfigManger {
    private List<PatrolMainConfig> configList = List.of();
    private Map<Integer, PatrolMainConfig> configMap = Map.of();

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
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      PatrolMainConfigChecker checker = new PatrolMainConfigChecker();
      List<PatrolMainConfig> newList = new ArrayList<>();
      Map<Integer, PatrolMainConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
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
          String parsedField13 = null;
          int parsedField14 = 0;
          String parsedField15 = null;
          String parsedField16 = null;
          String parsedField17 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("groupId"), fileName + ":行" + lineNumber + ":字段 groupId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("cost"), fileName + ":行" + lineNumber + ":字段 cost");
            parsedField3 = ly.ConfigValues.read(type3, values.get("weight"), fileName + ":行" + lineNumber + ":字段 weight");
            parsedField4 = ly.ConfigValues.read(type4, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField5 = ly.ConfigValues.read(type5, values.get("icon"), fileName + ":行" + lineNumber + ":字段 icon");
            parsedField6 = ly.ConfigValues.read(type6, values.get("dropGroupId"), fileName + ":行" + lineNumber + ":字段 dropGroupId");
            parsedField7 = ly.ConfigValues.read(type7, values.get("itemPre"), fileName + ":行" + lineNumber + ":字段 itemPre");
            parsedField8 = ly.ConfigValues.read(type8, values.get("heroNumLimit"), fileName + ":行" + lineNumber + ":字段 heroNumLimit");
            parsedField9 = ly.ConfigValues.read(type9, values.get("timeConsume"), fileName + ":行" + lineNumber + ":字段 timeConsume");
            parsedField10 = ly.ConfigValues.read(type10, values.get("extraDropGroupId"), fileName + ":行" + lineNumber + ":字段 extraDropGroupId");
            parsedField11 = ly.ConfigValues.read(type11, values.get("extraDropPro"), fileName + ":行" + lineNumber + ":字段 extraDropPro");
            parsedField12 = ly.ConfigValues.read(type12, values.get("lvLimit"), fileName + ":行" + lineNumber + ":字段 lvLimit");
            parsedField13 = ly.ConfigValues.read(type13, values.get("desc"), fileName + ":行" + lineNumber + ":字段 desc");
            parsedField14 = ly.ConfigValues.read(type14, values.get("startEvent"), fileName + ":行" + lineNumber + ":字段 startEvent");
            parsedField15 = ly.ConfigValues.read(type15, values.get("eventNum"), fileName + ":行" + lineNumber + ":字段 eventNum");
            parsedField16 = ly.ConfigValues.read(type16, values.get("eventTime"), fileName + ":行" + lineNumber + ":字段 eventTime");
            parsedField17 = ly.ConfigValues.read(type17, values.get("eventPro"), fileName + ":行" + lineNumber + ":字段 eventPro");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          PatrolMainConfig config = new PatrolMainConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (PatrolMainConfig config : configList) config.afterLoad();
      new PatrolMainConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<PatrolMainConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, PatrolMainConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "patrolMain.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
