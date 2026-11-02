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
 * File: PracticeGroupConfigManager
 */
public class PracticeGroupConfigManager implements InterfaceConfigManagerProxy {
  public static PracticeGroupConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(PracticeGroupConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new PracticeGroupConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "practiceGroup.xml"; }

  public static class PracticeGroupConfigManagerImpl extends AbstractConfigManger {
    private List<PracticeGroupConfig> configList = List.of();
    private Map<Integer, PracticeGroupConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      PracticeGroupConfigChecker checker = new PracticeGroupConfigChecker();
      List<PracticeGroupConfig> newList = new ArrayList<>();
      Map<Integer, PracticeGroupConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          String parsedField1 = null;
          int parsedField2 = 0;
          int parsedField3 = 0;
          String parsedField4 = null;
          int parsedField5 = 0;
          String parsedField6 = null;
          int parsedField7 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("name"), fileName + ":行" + lineNumber + ":字段 name");
            parsedField2 = ly.ConfigValues.read(type2, values.get("resource"), fileName + ":行" + lineNumber + ":字段 resource");
            parsedField3 = ly.ConfigValues.read(type3, values.get("show"), fileName + ":行" + lineNumber + ":字段 show");
            parsedField4 = ly.ConfigValues.read(type4, values.get("stage"), fileName + ":行" + lineNumber + ":字段 stage");
            parsedField5 = ly.ConfigValues.read(type5, values.get("unlock"), fileName + ":行" + lineNumber + ":字段 unlock");
            parsedField6 = ly.ConfigValues.read(type6, values.get("unlockText"), fileName + ":行" + lineNumber + ":字段 unlockText");
            parsedField7 = ly.ConfigValues.read(type7, values.get("quality"), fileName + ":行" + lineNumber + ":字段 quality");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          PracticeGroupConfig config = new PracticeGroupConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (PracticeGroupConfig config : configList) config.afterLoad();
      new PracticeGroupConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<PracticeGroupConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, PracticeGroupConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "practiceGroup.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
