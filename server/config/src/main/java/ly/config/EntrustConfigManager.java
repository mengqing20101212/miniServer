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
 * File: EntrustConfigManager
 */
public class EntrustConfigManager implements InterfaceConfigManagerProxy {
  public static EntrustConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(EntrustConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new EntrustConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "entrust.xml"; }

  public static class EntrustConfigManagerImpl extends AbstractConfigManger {
    private List<EntrustConfig> configList = List.of();
    private Map<Integer, EntrustConfig> configMap = Map.of();

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
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      EntrustConfigChecker checker = new EntrustConfigChecker();
      List<EntrustConfig> newList = new ArrayList<>();
      Map<Integer, EntrustConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          String parsedField5 = null;
          String parsedField6 = null;
          int parsedField7 = 0;
          int parsedField8 = 0;
          int parsedField9 = 0;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          String parsedField13 = null;
          String parsedField14 = null;
          String parsedField15 = null;
          String parsedField16 = null;
          int parsedField17 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("groupId"), fileName + ":行" + lineNumber + ":字段 groupId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("star"), fileName + ":行" + lineNumber + ":字段 star");
            parsedField3 = ly.ConfigValues.read(type3, values.get("unlockType"), fileName + ":行" + lineNumber + ":字段 unlockType");
            parsedField4 = ly.ConfigValues.read(type4, values.get("unlockCondition"), fileName + ":行" + lineNumber + ":字段 unlockCondition");
            parsedField5 = ly.ConfigValues.read(type5, values.get("unlockDec"), fileName + ":行" + lineNumber + ":字段 unlockDec");
            parsedField6 = ly.ConfigValues.read(type6, values.get("reward"), fileName + ":行" + lineNumber + ":字段 reward");
            parsedField7 = ly.ConfigValues.read(type7, values.get("drop"), fileName + ":行" + lineNumber + ":字段 drop");
            parsedField8 = ly.ConfigValues.read(type8, values.get("dropPercent"), fileName + ":行" + lineNumber + ":字段 dropPercent");
            parsedField9 = ly.ConfigValues.read(type9, values.get("time"), fileName + ":行" + lineNumber + ":字段 time");
            parsedField10 = ly.ConfigValues.read(type10, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField11 = ly.ConfigValues.read(type11, values.get("percent"), fileName + ":行" + lineNumber + ":字段 percent");
            parsedField12 = ly.ConfigValues.read(type12, values.get("heroNum"), fileName + ":行" + lineNumber + ":字段 heroNum");
            parsedField13 = ly.ConfigValues.read(type13, values.get("taskName"), fileName + ":行" + lineNumber + ":字段 taskName");
            parsedField14 = ly.ConfigValues.read(type14, values.get("timeGroup"), fileName + ":行" + lineNumber + ":字段 timeGroup");
            parsedField15 = ly.ConfigValues.read(type15, values.get("action"), fileName + ":行" + lineNumber + ":字段 action");
            parsedField16 = ly.ConfigValues.read(type16, values.get("position"), fileName + ":行" + lineNumber + ":字段 position");
            parsedField17 = ly.ConfigValues.read(type17, values.get("iconResId"), fileName + ":行" + lineNumber + ":字段 iconResId");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          EntrustConfig config = new EntrustConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (EntrustConfig config : configList) config.afterLoad();
      new EntrustConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<EntrustConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, EntrustConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "entrust.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
