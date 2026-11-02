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
 * File: HeadFrameConfigManager
 */
public class HeadFrameConfigManager implements InterfaceConfigManagerProxy {
  public static HeadFrameConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(HeadFrameConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new HeadFrameConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "headFrame.xml"; }

  public static class HeadFrameConfigManagerImpl extends AbstractConfigManger {
    private List<HeadFrameConfig> configList = List.of();
    private Map<Integer, HeadFrameConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      HeadFrameConfigChecker checker = new HeadFrameConfigChecker();
      List<HeadFrameConfig> newList = new ArrayList<>();
      Map<Integer, HeadFrameConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          int parsedField3 = 0;
          String parsedField4 = null;
          int parsedField5 = 0;
          int parsedField6 = 0;
          int parsedField7 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("desc"), fileName + ":行" + lineNumber + ":字段 desc");
            parsedField3 = ly.ConfigValues.read(type3, values.get("priority"), fileName + ":行" + lineNumber + ":字段 priority");
            parsedField4 = ly.ConfigValues.read(type4, values.get("value"), fileName + ":行" + lineNumber + ":字段 value");
            parsedField5 = ly.ConfigValues.read(type5, values.get("duration"), fileName + ":行" + lineNumber + ":字段 duration");
            parsedField6 = ly.ConfigValues.read(type6, values.get("isInitial"), fileName + ":行" + lineNumber + ":字段 isInitial");
            parsedField7 = ly.ConfigValues.read(type7, values.get("channel"), fileName + ":行" + lineNumber + ":字段 channel");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          HeadFrameConfig config = new HeadFrameConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (HeadFrameConfig config : configList) config.afterLoad();
      new HeadFrameConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<HeadFrameConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, HeadFrameConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "headFrame.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
