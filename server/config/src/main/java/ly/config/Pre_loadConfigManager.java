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
 * File: Pre_loadConfigManager
 */
public class Pre_loadConfigManager implements InterfaceConfigManagerProxy {
  public static Pre_loadConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(Pre_loadConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new Pre_loadConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "pre_load.xml"; }

  public static class Pre_loadConfigManagerImpl extends AbstractConfigManger {
    private List<Pre_loadConfig> configList = List.of();
    private Map<Integer, Pre_loadConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      Pre_loadConfigChecker checker = new Pre_loadConfigChecker();
      List<Pre_loadConfig> newList = new ArrayList<>();
      Map<Integer, Pre_loadConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          String parsedField6 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("desc"), fileName + ":行" + lineNumber + ":字段 desc");
            parsedField2 = ly.ConfigValues.read(type2, values.get("resid"), fileName + ":行" + lineNumber + ":字段 resid");
            parsedField3 = ly.ConfigValues.read(type3, values.get("restype"), fileName + ":行" + lineNumber + ":字段 restype");
            parsedField4 = ly.ConfigValues.read(type4, values.get("type"), fileName + ":行" + lineNumber + ":字段 type");
            parsedField5 = ly.ConfigValues.read(type5, values.get("holdstate"), fileName + ":行" + lineNumber + ":字段 holdstate");
            parsedField6 = ly.ConfigValues.read(type6, values.get("param"), fileName + ":行" + lineNumber + ":字段 param");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          Pre_loadConfig config = new Pre_loadConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (Pre_loadConfig config : configList) config.afterLoad();
      new Pre_loadConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<Pre_loadConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, Pre_loadConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "pre_load.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
