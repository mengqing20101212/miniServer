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
 * File: PassCheckConfigManager
 */
public class PassCheckConfigManager implements InterfaceConfigManagerProxy {
  public static PassCheckConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(PassCheckConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new PassCheckConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "passCheck.xml"; }

  public static class PassCheckConfigManagerImpl extends AbstractConfigManger {
    private List<PassCheckConfig> configList = List.of();
    private Map<Integer, PassCheckConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      PassCheckConfigChecker checker = new PassCheckConfigChecker();
      List<PassCheckConfig> newList = new ArrayList<>();
      Map<Integer, PassCheckConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          String parsedField3 = null;
          String parsedField4 = null;
          int parsedField5 = 0;
          int parsedField6 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField2 = ly.ConfigValues.read(type2, values.get("cost"), fileName + ":行" + lineNumber + ":字段 cost");
            parsedField3 = ly.ConfigValues.read(type3, values.get("reward"), fileName + ":行" + lineNumber + ":字段 reward");
            parsedField4 = ly.ConfigValues.read(type4, values.get("seniorReward"), fileName + ":行" + lineNumber + ":字段 seniorReward");
            parsedField5 = ly.ConfigValues.read(type5, values.get("key"), fileName + ":行" + lineNumber + ":字段 key");
            parsedField6 = ly.ConfigValues.read(type6, values.get("levelPrice"), fileName + ":行" + lineNumber + ":字段 levelPrice");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          PassCheckConfig config = new PassCheckConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (PassCheckConfig config : configList) config.afterLoad();
      new PassCheckConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<PassCheckConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, PassCheckConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "passCheck.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
