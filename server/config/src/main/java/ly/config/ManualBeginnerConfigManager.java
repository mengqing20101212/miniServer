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
 * File: ManualBeginnerConfigManager
 */
public class ManualBeginnerConfigManager implements InterfaceConfigManagerProxy {
  public static ManualBeginnerConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ManualBeginnerConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ManualBeginnerConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "manualBeginner.xml"; }

  public static class ManualBeginnerConfigManagerImpl extends AbstractConfigManger {
    private List<ManualBeginnerConfig> configList = List.of();
    private Map<Integer, ManualBeginnerConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("string");
      String fileName = configDir + File.separator + getConfigFileName();
      ManualBeginnerConfigChecker checker = new ManualBeginnerConfigChecker();
      List<ManualBeginnerConfig> newList = new ArrayList<>();
      Map<Integer, ManualBeginnerConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          String parsedField2 = null;
          int parsedField3 = 0;
          String parsedField4 = null;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("nextId"), fileName + ":行" + lineNumber + ":字段 nextId");
            parsedField2 = ly.ConfigValues.read(type2, values.get("showWord"), fileName + ":行" + lineNumber + ":字段 showWord");
            parsedField3 = ly.ConfigValues.read(type3, values.get("frontId"), fileName + ":行" + lineNumber + ":字段 frontId");
            parsedField4 = ly.ConfigValues.read(type4, values.get("fuzhu"), fileName + ":行" + lineNumber + ":字段 fuzhu");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          ManualBeginnerConfig config = new ManualBeginnerConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (ManualBeginnerConfig config : configList) config.afterLoad();
      new ManualBeginnerConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<ManualBeginnerConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ManualBeginnerConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "manualBeginner.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
