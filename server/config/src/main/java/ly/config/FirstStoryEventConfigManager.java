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
 * File: FirstStoryEventConfigManager
 */
public class FirstStoryEventConfigManager implements InterfaceConfigManagerProxy {
  public static FirstStoryEventConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(FirstStoryEventConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new FirstStoryEventConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "firstStoryEvent.xml"; }

  public static class FirstStoryEventConfigManagerImpl extends AbstractConfigManger {
    private List<FirstStoryEventConfig> configList = List.of();
    private Map<Integer, FirstStoryEventConfig> configMap = Map.of();

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
      String fileName = configDir + File.separator + getConfigFileName();
      FirstStoryEventConfigChecker checker = new FirstStoryEventConfigChecker();
      List<FirstStoryEventConfig> newList = new ArrayList<>();
      Map<Integer, FirstStoryEventConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          String parsedField3 = null;
          int parsedField4 = 0;
          int parsedField5 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField3 = ly.ConfigValues.read(type3, values.get("values"), fileName + ":行" + lineNumber + ":字段 values");
            parsedField4 = ly.ConfigValues.read(type4, values.get("flag"), fileName + ":行" + lineNumber + ":字段 flag");
            parsedField5 = ly.ConfigValues.read(type5, values.get("transitionType"), fileName + ":行" + lineNumber + ":字段 transitionType");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          FirstStoryEventConfig config = new FirstStoryEventConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (FirstStoryEventConfig config : configList) config.afterLoad();
      new FirstStoryEventConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<FirstStoryEventConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, FirstStoryEventConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "firstStoryEvent.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
