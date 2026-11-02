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
 * File: Drop1ConfigManager
 */
public class Drop1ConfigManager implements InterfaceConfigManagerProxy {
  public static Drop1ConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(Drop1ConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new Drop1ConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "drop1.xml"; }

  public static class Drop1ConfigManagerImpl extends AbstractConfigManger {
    private List<Drop1Config> configList = List.of();
    private Map<Integer, Drop1Config> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      Drop1ConfigChecker checker = new Drop1ConfigChecker();
      List<Drop1Config> newList = new ArrayList<>();
      Map<Integer, Drop1Config> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          String parsedField5 = null;
          String parsedField6 = null;
          String parsedField7 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("dropType"), fileName + ":行" + lineNumber + ":字段 dropType");
            parsedField2 = ly.ConfigValues.read(type2, values.get("des"), fileName + ":行" + lineNumber + ":字段 des");
            parsedField3 = ly.ConfigValues.read(type3, values.get("dropList"), fileName + ":行" + lineNumber + ":字段 dropList");
            parsedField4 = ly.ConfigValues.read(type4, values.get("itemPro"), fileName + ":行" + lineNumber + ":字段 itemPro");
            parsedField5 = ly.ConfigValues.read(type5, values.get("itemRelativePro"), fileName + ":行" + lineNumber + ":字段 itemRelativePro");
            parsedField6 = ly.ConfigValues.read(type6, values.get("dropTimes"), fileName + ":行" + lineNumber + ":字段 dropTimes");
            parsedField7 = ly.ConfigValues.read(type7, values.get("dropCounts"), fileName + ":行" + lineNumber + ":字段 dropCounts");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          Drop1Config config = new Drop1Config(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (Drop1Config config : configList) config.afterLoad();
      new Drop1ConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<Drop1Config> getConfigList() {
      return configList;
    }

    public Map<Integer, Drop1Config> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "drop1.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
