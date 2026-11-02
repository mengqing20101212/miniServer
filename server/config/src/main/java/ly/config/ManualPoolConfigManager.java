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
 * File: ManualPoolConfigManager
 */
public class ManualPoolConfigManager implements InterfaceConfigManagerProxy {
  public static ManualPoolConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ManualPoolConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ManualPoolConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "manualPool.xml"; }

  public static class ManualPoolConfigManagerImpl extends AbstractConfigManger {
    private List<ManualPoolConfig> configList = List.of();
    private Map<Integer, ManualPoolConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      ManualPoolConfigChecker checker = new ManualPoolConfigChecker();
      List<ManualPoolConfig> newList = new ArrayList<>();
      Map<Integer, ManualPoolConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          String parsedField2 = null;
          String parsedField3 = null;
          String parsedField4 = null;
          String parsedField5 = null;
          String parsedField6 = null;
          String parsedField7 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField2 = ly.ConfigValues.read(type2, values.get("regularPoolDaily"), fileName + ":行" + lineNumber + ":字段 regularPoolDaily");
            parsedField3 = ly.ConfigValues.read(type3, values.get("randomNumDaily"), fileName + ":行" + lineNumber + ":字段 randomNumDaily");
            parsedField4 = ly.ConfigValues.read(type4, values.get("poolGroupDaily"), fileName + ":行" + lineNumber + ":字段 poolGroupDaily");
            parsedField5 = ly.ConfigValues.read(type5, values.get("regularPoolWeek"), fileName + ":行" + lineNumber + ":字段 regularPoolWeek");
            parsedField6 = ly.ConfigValues.read(type6, values.get("randomNumWeek"), fileName + ":行" + lineNumber + ":字段 randomNumWeek");
            parsedField7 = ly.ConfigValues.read(type7, values.get("poolGroupWeek"), fileName + ":行" + lineNumber + ":字段 poolGroupWeek");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ManualPoolConfig config = new ManualPoolConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ManualPoolConfig config : configList) config.afterLoad();
      new ManualPoolConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ManualPoolConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ManualPoolConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "manualPool.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
