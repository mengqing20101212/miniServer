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
 * File: SystemmessageConfigManager
 */
public class SystemmessageConfigManager implements InterfaceConfigManagerProxy {
  public static SystemmessageConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(SystemmessageConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new SystemmessageConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "systemmessage.xml"; }

  public static class SystemmessageConfigManagerImpl extends AbstractConfigManger {
    private List<SystemmessageConfig> configList = List.of();
    private Map<Integer, SystemmessageConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      SystemmessageConfigChecker checker = new SystemmessageConfigChecker();
      List<SystemmessageConfig> newList = new ArrayList<>();
      Map<Integer, SystemmessageConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          String parsedField0 = null;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          String parsedField6 = null;
          String parsedField7 = null;
          int parsedField8 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("comment"), fileName + ":行" + lineNumber + ":字段 comment");
            parsedField1 = ly.ConfigValues.read(type1, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField2 = ly.ConfigValues.read(type2, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField3 = ly.ConfigValues.read(type3, values.get("item_type"), fileName + ":行" + lineNumber + ":字段 item_type");
            parsedField4 = ly.ConfigValues.read(type4, values.get("speed"), fileName + ":行" + lineNumber + ":字段 speed");
            parsedField5 = ly.ConfigValues.read(type5, values.get("time"), fileName + ":行" + lineNumber + ":字段 time");
            parsedField6 = ly.ConfigValues.read(type6, values.get("message1"), fileName + ":行" + lineNumber + ":字段 message1");
            parsedField7 = ly.ConfigValues.read(type7, values.get("message2"), fileName + ":行" + lineNumber + ":字段 message2");
            parsedField8 = ly.ConfigValues.read(type8, values.get("delayTime"), fileName + ":行" + lineNumber + ":字段 delayTime");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          SystemmessageConfig config = new SystemmessageConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (SystemmessageConfig config : configList) config.afterLoad();
      new SystemmessageConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<SystemmessageConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, SystemmessageConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "systemmessage.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
